package com.cricket.api.cricketapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class CricketSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CricketSpringBootApplication.class, args);
    }

}
