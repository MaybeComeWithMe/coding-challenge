package com.ubitricity.codingchallenge.repository;

import com.ubitricity.codingchallenge.exception.EntityNotFoundException;
import com.ubitricity.codingchallenge.model.ChargePoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Rollback
@DataJpaTest
class ChargePointRepositoryTest {

    @Autowired
    private ChargePointRepository underTest;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:fixture/charge_point_inserts.sql"})
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"classpath:fixture/charge_point_truncate.sql"})
    void findByIdRepositoryTest() {
        Optional<ChargePoint> byId = underTest.findById(1L);
        assertNotNull(byId.orElseThrow(EntityNotFoundException::new));
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:fixture/charge_point_inserts.sql"})
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"classpath:fixture/charge_point_truncate.sql"})
    void findAllRepositoryTest() {
        List<ChargePoint> all = underTest.findAll();
        assertNotNull(all);
        assertThat(all).isNotNull().isNotEmpty();
    }
}
