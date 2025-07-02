package com.api.controller;

import com.api.entity.PercentageData;
import com.api.entity.UsageData;
import com.api.repository.PercentageDataRepository;
import com.api.repository.UsageDataRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for energy data.
 */
@RestController
@RequestMapping("/energy")
public class EnergyController {

    private final UsageDataRepository usageDataRepository;
    private final PercentageDataRepository percentageRepository;

    /**
     * Constructor injection.
     * Spring will automatically create the repository object and give it to this class
     * @param repository
     */
    public EnergyController(UsageDataRepository usageDataRepository, PercentageDataRepository repository) {
        this.usageDataRepository = usageDataRepository;
        this.percentageRepository = repository;
    }

    /**
     * Returns the most recent percentage data from the table percentage_data
     * @return latest PercentageData
     */
    @GetMapping("/current")
    public PercentageData getCurrentEnergy() {
        // Find the newest row (latest hour) from the table
        return percentageRepository.findFirstByOrderByHourDesc();
    }

    /**
     * Returns a list of percentage data between two timestamps
     * Example URL: /energy/historical?start=2025-06-20T00:00:00&end=2025-07-20T00:00:00
     *
     * @param start start timestamp (example: "2025-06-20T00:00:00")
     * @param end end timestamp (example: "2025-07-20T00:00:00")
     * @return list of PercentageData entries
     */
    @GetMapping("/historical")
    public List<UsageData> getHistoricalEnergy(
            @RequestParam String start,
            @RequestParam String end) {
        // Convert input strings to LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.parse(start);
        LocalDateTime endDateTime = LocalDateTime.parse(end);

        // Find all rows between the start and end time, ordered ascending
        return usageDataRepository.findAllByHourBetweenOrderByHourAsc(startDateTime, endDateTime);
    }
}