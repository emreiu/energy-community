package com.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EnergyController {

    @GetMapping("/energy/current")
    public Map<String, Object> getCurrentEnergy() {
        // Mock-Data just for testing the api
        return Map.of(
                "hour", "2025-06-28T15:00:00",
                "community_depleted", 80.0,
                "grid_portion", 20.0
        );
    }

    @GetMapping("/energy/historical")
    public List<Map<String, Object>> getHistoricalEnergy(
            @RequestParam String start,
            @RequestParam String end) {
        // Mock-Data just for testing the api
        return List.of(
                Map.of(
                        "hour", "2025-06-28T13:00:00",
                        "community_depleted", 70.0,
                        "grid_portion", 30.0
                ),
                Map.of(
                        "hour", "2025-06-28T14:00:00",
                        "community_depleted", 85.0,
                        "grid_portion", 15.0
                )
        );
    }
}
