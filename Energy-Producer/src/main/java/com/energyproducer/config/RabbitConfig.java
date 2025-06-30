package com.energyproducer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "energy.queue";

    @Bean
    public Queue energyQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
