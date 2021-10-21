package com.ubitricity.codingchallenge.controller;

import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.service.ChargePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ubitricity.codingchallenge.model.ChargePoint.Status.AVAILABLE;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.OCCUPIED;

@Slf4j
@RestController
@RequestMapping("/api/v0/charge-points")
public class ChargePointController {

    private final ChargePointService chargePointService;

    @Autowired
    public ChargePointController(ChargePointService chargePointService) {
        this.chargePointService = chargePointService;
    }

    @GetMapping
    public List<ChargePointDto> getChargePoints() {
        log.debug("Entering getChargePoints method");
        return chargePointService.getChargePoints();
    }

    @PostMapping("/{cpId}/plug")
    public ChargePointDto plugChargePoint(@PathVariable(name = "cpId") long chargePointId) {
        log.debug("Entering plug method");
        return chargePointService.toggleChargePoint(chargePointId, OCCUPIED);
    }

    @PostMapping("/{cpId}/unplug")
    public ChargePointDto unplugChargePoint(@PathVariable(name = "cpId") long chargePointId) {
        log.debug("Entering plug method");
        return chargePointService.toggleChargePoint(chargePointId, AVAILABLE);
    }
}
