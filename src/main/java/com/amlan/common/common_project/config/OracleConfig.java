package com.amlan.common.common_project.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amlan.common.common_project.impl.OracleSagaIdImpl;

@Configuration
@ConditionalOnProperty(value ="timeout.management.enabled",havingValue="true")
@EntityScan(basePackages = "com.amlan.common.common_project.entity")
public class OracleConfig {

    @Bean
    public OracleSagaIdImpl sagaImpl(){
        return new OracleSagaIdImpl();
    }
}
