package com.usageservice;

import com.usageservice.dto.EnergyMessage;
import com.usageservice.entity.UsageData;
import com.usageservice.repository.UsageDataRepository;
import com.usageservice.service.UsageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsageServiceApplicationTests {

    @Mock
    private UsageDataRepository mockRepository;

    @Mock
    private RabbitTemplate mockRabbitTemplate;

    private UsageService service;

    @BeforeEach
    void setUp() {
        service = new UsageService(mockRepository, mockRabbitTemplate);
    }

    @Test
    void processProducerMessage_savesProducedEnergy() {
        // arrange
        EnergyMessage msg = new EnergyMessage();
        msg.setType("PRODUCER");
        msg.setAssociation("COMMUNITY");
        msg.setKwh(4.5);
        msg.setDatetime("2025-07-01T10:00:00");

        LocalDateTime hour = LocalDateTime.parse(msg.getDatetime());
        UsageData existing = new UsageData();
        existing.setHour(hour);
        existing.setCommunityProduced(0.0);
        existing.setCommunityUsed(0.0);
        existing.setGridUsed(0.0);

        when(mockRepository.findById(hour)).thenReturn(Optional.of(existing));

        service.processMessage(msg);

        verify(mockRepository).save(argThat(saved ->
                saved.getCommunityProduced() == 4.5 &&
                        saved.getHour().equals(hour)
        ));
    }
}
