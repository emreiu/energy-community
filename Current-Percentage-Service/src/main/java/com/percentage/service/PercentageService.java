package com.percentage.service;

import com.percentage.entity.PercentageData;
import com.percentage.entity.UsageData;
import com.percentage.repository.PercentageDataRepository;
import com.percentage.repository.UsageDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service that calculates the percentages and saves them.
 */
@Service
public class PercentageService {

    private final UsageDataRepository usageDataRepository;
    private final PercentageDataRepository percentageDataRepository;

    public PercentageService(UsageDataRepository usageDataRepository, PercentageDataRepository percentageDataRepository) {
        this.usageDataRepository = usageDataRepository;
        this.percentageDataRepository = percentageDataRepository;
    }

    /**
     * Loads usage data for the given hour and calculates percentages.
     * @param hour the hour timestamp
     */
    public void calculateAndSave(LocalDateTime hour) {
        UsageData usageData = usageDataRepository.findById(hour).orElseThrow(() -> new RuntimeException("Usage data not found for hour: " + hour));

        double produced = usageData.getCommunityProduced();
        double used = usageData.getCommunityUsed();
        double grid = usageData.getGridUsed();

        double communityDepleted = produced > 0 ? Math.min(100.0, (used / produced) * 100.0) : 100.0;
        double total = used + grid;
        double gridPortion = total > 0 ? (grid / total) * 100.0 : 0.0;

        PercentageData percentageData = new PercentageData();
        percentageData.setHour(hour);
        percentageData.setCommunityDepleted(communityDepleted);
        percentageData.setGridPortion(gridPortion);

        percentageDataRepository.save(percentageData);

        System.out.println("Calculated percentages for hour " + hour + ": communityDepleted=" + communityDepleted + " gridPortion=" + gridPortion);
    }
}