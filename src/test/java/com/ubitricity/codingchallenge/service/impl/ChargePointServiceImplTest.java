package com.ubitricity.codingchallenge.service.impl;

import com.ubitricity.codingchallenge.common.MockedDatasource;
import com.ubitricity.codingchallenge.dto.ChargePointDto;
import com.ubitricity.codingchallenge.exception.EntityNotFoundException;
import com.ubitricity.codingchallenge.exception.EntityValidationException;
import com.ubitricity.codingchallenge.model.ChargePoint;
import com.ubitricity.codingchallenge.repository.ChargePointRepository;
import com.ubitricity.codingchallenge.service.ChargePointService;
import com.ubitricity.codingchallenge.service.converter.Converter;
import com.ubitricity.codingchallenge.util.ChargePointUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ubitricity.codingchallenge.model.ChargePoint.Status.AVAILABLE;
import static com.ubitricity.codingchallenge.model.ChargePoint.Status.OCCUPIED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = MockedDatasource.class)
public class ChargePointServiceImplTest {

    ChargePointService underTest;
    Converter converter;

    final ModelMapper modelMapper = new ModelMapper();

    @Mock
    ChargePointRepository chargePointRepository;

    @BeforeEach
    void setUpEach() {
        converter = new Converter(modelMapper);
        underTest = spy(new ChargePointServiceImpl(chargePointRepository, converter, "100"));
    }

    @Test
    void getChargePointsTest() {
        List<ChargePoint> chargePoints = Collections.singletonList(ChargePointUtilities.getAvailableChargePoint());
        when(chargePointRepository.findAll()).thenReturn(chargePoints);

        List<ChargePointDto> report = underTest.getChargePoints();
        assertThat(report).isNotNull().isNotEmpty();
        assertThat(report.size()).isEqualTo(chargePoints.size());
        verify(chargePointRepository, times(1)).findAll();
    }

    @Test
    void toggleChargePointEntityNotFoundTest() {
        when(chargePointRepository.findById(eq(1L))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> underTest.toggleChargePoint(1L, AVAILABLE));
    }

    @Test
    void toggleChargePointSameStatusTest() {
        when(chargePointRepository.findById(eq(1L))).thenReturn(Optional.of(ChargePointUtilities.getAvailableChargePoint()));
        assertThrows(EntityValidationException.class, () -> underTest.toggleChargePoint(1L, AVAILABLE));
    }

    @Test
    void toggleChargePointTest() {
        ChargePoint chargePoint = ChargePointUtilities.getAvailableChargePoint();
        ChargePoint occupiedChargePoint = ChargePointUtilities.getOccupiedChargePoint();
        when(chargePointRepository.findById(eq(1L))).thenReturn(Optional.of(chargePoint));
        when(chargePointRepository.findAll()).thenReturn(Collections.singletonList(occupiedChargePoint));

        //doReturn null for no further interactions with save methods
        doReturn(null).
                when(chargePointRepository).save(any(ChargePoint.class));
        doReturn(null).
                when(chargePointRepository).saveAll(any());

        underTest.toggleChargePoint(1L, OCCUPIED);
        verify(chargePointRepository, times(2)).findById(anyLong());
        verify(chargePointRepository, times(1)).save(any(ChargePoint.class));
    }
}
