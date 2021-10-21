package com.ubitricity.codingchallenge.dto;

import com.ubitricity.codingchallenge.model.ChargePoint.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargePointDto {
    private Long id;
    private Status status;
    private Integer amperes;
}
