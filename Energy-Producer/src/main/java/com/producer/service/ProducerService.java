package com.producer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producer.dto.EnergyMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Service that periodically sends energy production messages to RabbitMQ.
 */
@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    @Value("${app.queue}")
    private String queueName;

    /**
     * Constructor for dependency injection.
     *
     * @param rabbitTemplate RabbitMQ template
     * @param objectMapper   JSON serializer
     */
    public ProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Scheduled method to create and send a new PRODUCER message.
     * Runs every 10 seconds for demonstration purposes.
     */
    @Scheduled(fixedRate = 10000)
    public void sendEnergyMessage() {
        double kwhProduced = generateKwh();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        EnergyMessage message = new EnergyMessage();
        message.setType("PRODUCER");
        message.setAssociation("COMMUNITY");
        message.setKwh(kwhProduced);
        message.setDatetime(timestamp);

        try {
            String json = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(queueName, json);
            System.out.println("Sent message: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a semi-random kWh value simulating production.
     *
     * @return generated kWh
     */
    private double generateKwh() {
        // For simplicity, simulate higher production during day hours
        int hour = LocalDateTime.now().getHour();
        double base = (hour >= 6 && hour <= 18) ? 0.002 : 0.0005;
        return base + (random.nextDouble() * 0.002);
    }
}