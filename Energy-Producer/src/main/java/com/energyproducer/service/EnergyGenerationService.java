package com.energyproducer.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EnergyGenerationService {
    private final Random random = new Random();
    private final WeatherService weatherService;

    public EnergyGenerationService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public double generateEnergyKwh() {
        // Grundwert: 0.002–0.005 kWh pro Minute * Sonnenfaktor
        double base = 0.002 + random.nextDouble() * 0.003;
        return base * weatherService.getSunFactor();
    }
}
