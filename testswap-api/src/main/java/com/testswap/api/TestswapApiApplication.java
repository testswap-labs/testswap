package com.testswap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.testswap.api.config.AppProperties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(AppProperties.class)
public class TestswapApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestswapApiApplication.class, args);
    }
}
