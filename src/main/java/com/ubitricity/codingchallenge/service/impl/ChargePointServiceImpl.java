package com.ubitricity.codingchallenge.service.impl;

import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.exception.EntityNotFoundException;
import com.ubitricity.codingchallenge.exception.EntityValidationException;
import com.ubitricity.codingchallenge.model.ChargePoint;
import com.ubitricity.codingchallenge.model.ChargePoint.Status;
import com.ubitricity.codingchallenge.repository.ChargePointRepository;
import com.ubitricity.codingchallenge.service.ChargePointService;
import com.ubitricity.codingchallenge.service.converter.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.ubitricity.codingchallenge.model.ChargePoint.ChargeType.*;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.AVAILABLE;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.OCCUPIED;
import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ChargePointServiceImpl implements ChargePointService {

    private final int chargePointsMaxAmperes;

    private final ChargePointRepository chargePointRepository;
    private final Converter converter;

    @Autowired
    public ChargePointServiceImpl(ChargePointRepository chargePointRepository, Converter converter,
                                  @Value("${chargepoint.amperes.max}") String chargePointMaxAmperes) {
        this.chargePointRepository = chargePointRepository;
        this.converter = converter;
        this.chargePointsMaxAmperes = parseInt(chargePointMaxAmperes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargePointDto> getChargePoints() {
        return chargePointRepository.findAll().stream()
                .map(converter::convertToDto)
                .sorted(comparing(ChargePointDto::getId))
                .collect(toList());
    }

    @Override
    @Transactional
    public ChargePointDto toggleChargePoint(Long id, Status status) {
        ChargePoint chargePoint = chargePointRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if (chargePoint.getStatus().equals(status)) {
            throw new EntityValidationException("Charge point is already " + status.name().toLowerCase());
        }

        chargePoint.setStatus(status);

        if (AVAILABLE.equals(chargePoint.getStatus())) {
            chargePoint.setChargeType(OFF);
            chargePoint.setLastPlugged(null);
        } else {
            chargePoint.setChargeType(FAST);
            chargePoint.setLastPlugged(new Date());
        }

        chargePointRepository.save(chargePoint);

        distributePower();
        return converter.convertToDto(chargePointRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    private synchronized void distributePower() {
        List<ChargePoint> occupiedChargePoints = chargePointRepository.findAll().stream()
                .filter(cp -> OCCUPIED.equals(cp.getStatus()))
                .collect(toList());

        final int countOfOccupiedCP = occupiedChargePoints.size();

        occupiedChargePoints.sort(comparing(ChargePoint::getLastPlugged).reversed());

        int consumedAmperes = 0;
        for (int i = 0; i < countOfOccupiedCP; i++) {
            long chargesToPlug = countOfOccupiedCP - i - 1;
            long minConsumesAmperes = chargesToPlug * SLOW.getAmperes();

            ChargePoint cp = occupiedChargePoints.get(i);

            if (chargePointsMaxAmperes - consumedAmperes - minConsumesAmperes >= FAST.getAmperes()) {
                cp.setChargeType(FAST);
            } else {
                cp.setChargeType(SLOW);
            }

            consumedAmperes += cp.getChargeType().getAmperes();
        }

        chargePointRepository.saveAll(occupiedChargePoints);
    }
}
