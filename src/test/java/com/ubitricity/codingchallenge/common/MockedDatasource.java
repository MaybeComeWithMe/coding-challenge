package com.ubitricity.codingchallenge.common;

import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class MockedDatasource {

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Bean
    public DataSource dataSource() {
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}
