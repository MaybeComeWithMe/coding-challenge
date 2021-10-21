package com.ubitricity.codingchallenge.controller;

import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.service.ChargePointService;
import com.ubitricity.codingchallenge.util.ChargePointUtilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.ubitricity.codingchallenge.model.ChargePoint.Status.AVAILABLE;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.OCCUPIED;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChargePointController.class)
public class ChargePointControllerTest {

    @MockBean
    ChargePointService chargePointService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getReportTest() throws Exception {
        ChargePointDto chargePointDto = ChargePointUtilities.getChargePointDto();
        List<ChargePointDto> chargePointDtos = Collections.singletonList(chargePointDto);
        when(chargePointService.getChargePoints()).thenReturn(chargePointDtos);

        mockMvc.perform(get("/api/v0/charge-points")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(chargePointDtos.size()))
                .andExpect(jsonPath("$[0].id").value(chargePointDto.getId()))
                .andExpect(jsonPath("$[0].status").value(chargePointDto.getStatus().name()))
                .andExpect(jsonPath("$[0].amperes").value(chargePointDto.getAmperes()));
    }

    @Test
    public void plugChargePointsTest() throws Exception {
        ChargePointDto chargePointDto = ChargePointUtilities.getOccupiedChargePointDto();
        when(chargePointService.toggleChargePoint(eq(1L), eq(OCCUPIED)))
                .thenReturn(chargePointDto);

        mockMvc.perform(post("/api/v0/charge-points/1/plug")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chargePointDto.getId()))
                .andExpect(jsonPath("$.status").value(chargePointDto.getStatus().name()))
                .andExpect(jsonPath("$.amperes").value(chargePointDto.getAmperes()));
    }

    @Test
    public void unplugChargePointsTest() throws Exception {
        ChargePointDto chargePointDto = ChargePointUtilities.getAvailableChargePointDto();
        when(chargePointService.toggleChargePoint(eq(1L), eq(AVAILABLE)))
                .thenReturn(chargePointDto);

        mockMvc.perform(post("/api/v0/charge-points/1/unplug")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chargePointDto.getId()))
                .andExpect(jsonPath("$.status").value(chargePointDto.getStatus().name()))
                .andExpect(jsonPath("$.amperes").value(chargePointDto.getAmperes()));
    }
}
