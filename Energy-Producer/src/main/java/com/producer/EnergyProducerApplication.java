package com.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**

 Main application class for Energy Producer.*/
@SpringBootApplication
@EnableScheduling
public class EnergyProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyProducerApplication.class, args);
    }
}