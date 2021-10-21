package com.ubitricity.codingchallenge.service;

import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.model.ChargePoint;
import com.ubitricity.codingchallenge.model.ChargePoint.Status;

import java.util.List;

public interface ChargePointService {
    List<ChargePointDto> getChargePoints();
    ChargePointDto toggleChargePoint(Long id, Status status);
}
