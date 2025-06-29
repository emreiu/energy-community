package com.usageservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usageservice.dto.EnergyMessage;
import com.usageservice.service.UsageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener component for receiving energy messages from RabbitMQ.
 */
@Component
public class EnergyMessageListener {

    private final UsageService usageService;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for dependency injection.
     *
     * @param usageService the service to process the energy data
     * @param objectMapper the object mapper for JSON deserialization
     */
    public EnergyMessageListener(UsageService usageService, ObjectMapper objectMapper) {
        this.usageService = usageService;
        this.objectMapper = objectMapper;
    }

    /**
     * Receives and processes messages from the input queue.
     *
     * @param messageJson the raw JSON string from RabbitMQ
     */
    @RabbitListener(queues = "${app.queues.input}")
    public void handleMessage(String messageJson) {
        try {
            // Deserialize JSON into EnergyMessage DTO
            EnergyMessage message = objectMapper.readValue(messageJson, EnergyMessage.class);
            // Process the message using the service
            usageService.processMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
