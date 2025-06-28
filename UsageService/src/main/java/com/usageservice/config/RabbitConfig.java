package com.usageservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ queues.
 */
@Configuration
public class RabbitConfig {

    @Value("${app.queues.input}")
    private String inputQueueName;

    @Value("${app.queues.update}")
    private String updateQueueName;

    /**
     * Declares the queue for receiving energy messages from producers and users.
     *
     * @return the input queue
     */
    @Bean
    public Queue inputQueue() {
        return new Queue(inputQueueName, true);
    }

    /**
     * Declares the queue for sending update notifications to the Current Percentage Service.
     *
     * @return the update queue
     */
    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueueName, true);
    }
}
