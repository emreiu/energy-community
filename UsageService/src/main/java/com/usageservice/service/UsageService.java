package com.usageservice.service;

import com.usageservice.dto.EnergyMessage;
import com.usageservice.entity.UsageData;
import com.usageservice.repository.UsageDataRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Service class for processing incoming energy messages.
 * This class updates the usage_data table and sends update notifications.
 */
@Service
public class UsageService {

    private final UsageDataRepository repository;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Name of the update queue (injected from properties).
     */
    @Value("${app.queues.update}")
    private String updateQueue;

    /**
     * Constructor for dependency injection.
     *
     * @param repository the repository for usage_data
     * @param rabbitTemplate the RabbitTemplate for sending messages
     */
    public UsageService(UsageDataRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Processes an incoming EnergyMessage:
     * - Aggregates data into the usage_data table
     * - Sends an update notification after processing
     *
     * @param message the energy message to process
     */
    public void processMessage(EnergyMessage message) {
        // Convert datetime to hour timestamp
        LocalDateTime hourTimestamp = LocalDateTime.parse(message.getDatetime()).truncatedTo(ChronoUnit.HOURS);

        // Load existing data or create new
        UsageData usageData = repository.findById(hourTimestamp).orElseGet(() -> {
            UsageData newData = new UsageData();
            newData.setHour(hourTimestamp);
            return newData;
        });

        // Update values depending on message type
        if ("PRODUCER".equalsIgnoreCase(message.getType())) {
            double newProduced = usageData.getCommunityProduced() + message.getKwh();
            usageData.setCommunityProduced(newProduced);
        } else if ("USER".equalsIgnoreCase(message.getType())) {
            double totalCommunityUsed = usageData.getCommunityUsed() + message.getKwh();
            double availableCommunity = usageData.getCommunityProduced();

            if (totalCommunityUsed <= availableCommunity) {
                // All consumption can be covered by community
                usageData.setCommunityUsed(totalCommunityUsed);
            } else {
                // Partially covered by community, remainder from grid
                usageData.setCommunityUsed(availableCommunity);
                double gridUsage = totalCommunityUsed - availableCommunity;
                usageData.setGridUsed(usageData.getGridUsed() + gridUsage);
            }
        }

        // Save the updated record
        repository.save(usageData);

        // Send update notification to the update queue
        rabbitTemplate.convertAndSend(updateQueue, hourTimestamp.toString());
    }
}
