package com.ubitricity.codingchallenge;

import com.ubitricity.codingchallenge.controller.ChargePointController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CodingChallengeApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ChargePointController chargePointController;

    @Test
    public void testContextLoads() {
        assertTrue(applicationContext.getId().contains("codingchallenge"));
        assertThat(chargePointController).isNotNull();
    }
}
