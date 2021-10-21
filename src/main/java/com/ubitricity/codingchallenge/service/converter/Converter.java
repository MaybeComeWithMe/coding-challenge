package com.ubitricity.codingchallenge.service.converter;

import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.model.ChargePoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    private final ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ChargePointDto convertToDto(ChargePoint chargePoint) {
        ChargePointDto chargePointDto = modelMapper.map(chargePoint, ChargePointDto.class);
        chargePointDto.setAmperes(chargePoint.getChargeType().getAmperes());
        return chargePointDto;
    }
}
