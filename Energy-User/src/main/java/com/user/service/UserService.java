package com.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.dto.EnergyMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Service that periodically sends energy usage messages to RabbitMQ.
 */
@Service
public class UserService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    @Value("${app.queue}")
    private String queueName;

    public UserService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Scheduled method to create and send a new USER message.
     * Runs every 10 seconds.
     */
    @Scheduled(fixedRate = 10000)
    public void sendEnergyMessage() {
        double kwhUsed = generateKwh();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        EnergyMessage message = new EnergyMessage();
        message.setType("USER");
        message.setAssociation("COMMUNITY");
        message.setKwh(kwhUsed);
        message.setDatetime(timestamp);

        try {
            String json = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(queueName, json);
            System.out.println("Sent usage message: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a semi-random kWh usage value.
     * More consumption in morning (7-10) and evening (18-22).
     */
    private double generateKwh() {
        int hour = LocalDateTime.now().getHour();
        double base;

        if (hour >= 7 && hour <= 10) {
            base = 0.002;
        } else if (hour >= 18 && hour <= 22) {
            base = 0.0025;
        } else {
            base = 0.001;
        }
        return base + (random.nextDouble() * 0.001);
    }
}