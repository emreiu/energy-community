package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**

 Main application class for Energy User.*/
@SpringBootApplication
@EnableScheduling
public class EnergyUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyUserApplication.class, args);
    }
}