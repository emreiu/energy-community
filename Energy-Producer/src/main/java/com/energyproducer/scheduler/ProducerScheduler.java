package com.energyproducer.scheduler;

import com.energyproducer.config.RabbitConfig;
import com.energyproducer.dto.EnergyMessage;
import com.energyproducer.service.EnergyGenerationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProducerScheduler {
    private final RabbitTemplate rabbitTemplate;
    private final EnergyGenerationService generationService;

    public ProducerScheduler(RabbitTemplate rabbitTemplate, EnergyGenerationService generationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.generationService = generationService;
    }

    // Wiederholt sich alle 1–5 Sekunden zufällig
    @Scheduled(fixedDelayString = "#{T(java.util.concurrent.ThreadLocalRandom).current().nextInt(1000, 5000)}")
    public void sendEnergyMessage() {
        double kwh = generationService.generateEnergyKwh();
        EnergyMessage message = new EnergyMessage(kwh, LocalDateTime.now());
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, message);
        System.out.printf("[EnergyProducer] Sent %.6f kWh at %s%n", kwh, message.getDatetime());
    }
}
