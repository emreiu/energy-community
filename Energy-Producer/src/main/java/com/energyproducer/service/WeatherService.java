package com.energyproducer.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WeatherService {
    private final Random random = new Random();

    public double getSunFactor() {
        // Simuliert Sonnenfaktor: 0.5 (bewölkt) bis 1.0 (sonnig)
        return 0.5 + random.nextDouble() * 0.5;
    }
}
