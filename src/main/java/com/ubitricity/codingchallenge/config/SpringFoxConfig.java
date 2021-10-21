package com.ubitricity.codingchallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .paths(any())
                .apis(basePackage("com.ubitricity.codingchallenge.controller"))
                .build();
    }
}

