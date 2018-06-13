package com.chirq.study.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@ComponentScan("com.chirq.study")
@EnableAutoConfiguration
 @PropertySource("classpath:config/study.properties")
@PropertySources(value = { @PropertySource("classpath:application.properties") })
@ImportResource(locations = { "classpath:spring/*.xml" })
public class ChirqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChirqApplication.class, args);
    }
}
