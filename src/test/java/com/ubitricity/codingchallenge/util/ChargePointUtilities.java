package com.ubitricity.codingchallenge.util;

import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.model.ChargePoint;

import java.util.Date;

import static com.ubitricity.codingchallenge.model.ChargePoint.ChargeType.*;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.AVAILABLE;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.OCCUPIED;

public class ChargePointUtilities {

    private ChargePointUtilities() {
    }

    public static ChargePointDto getChargePointDto() {

        return ChargePointDto.builder()
                .id(1L)
                .status(AVAILABLE)
                .amperes(SLOW.getAmperes())
                .build();
    }

    public static ChargePointDto getOccupiedChargePointDto() {

        return ChargePointDto.builder()
                .id(1L)
                .status(OCCUPIED)
                .amperes(FAST.getAmperes())
                .build();
    }

    public static ChargePointDto getAvailableChargePointDto() {

        return ChargePointDto.builder()
                .id(1L)
                .status(AVAILABLE)
                .amperes(OFF.getAmperes())
                .build();
    }


    public static ChargePoint getAvailableChargePoint() {

        return ChargePoint.builder()
                .id(1L)
                .status(AVAILABLE)
                .chargeType(OFF)
                .lastPlugged(new Date())
                .build();
    }

    public static ChargePoint getOccupiedChargePoint() {

        return ChargePoint.builder()
                .id(1L)
                .status(OCCUPIED)
                .chargeType(FAST)
                .lastPlugged(new Date())
                .build();
    }
}
