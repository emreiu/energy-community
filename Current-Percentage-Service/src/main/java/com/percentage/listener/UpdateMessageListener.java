package com.percentage.listener;

import com.percentage.service.PercentageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Listener for update notifications.
 */
@Component
public class UpdateMessageListener {

    private final PercentageService percentageService;

    public UpdateMessageListener(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    /**
     * Handles incoming update messages.
     * The message payload is expected to be the hour timestamp string.
     */
    @RabbitListener(queues = "energy-updates-queue")
    public void receiveMessage(String hourString) {
        LocalDateTime hour = LocalDateTime.parse(hourString);
        System.out.println("Received update message for hour: " + hour);
        percentageService.calculateAndSave(hour);}
}