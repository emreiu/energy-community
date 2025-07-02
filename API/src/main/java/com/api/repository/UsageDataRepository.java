package com.api.repository;

import com.api.entity.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageDataRepository extends JpaRepository<UsageData, LocalDateTime> {
    List<UsageData> findAllByHourBetweenOrderByHourAsc(LocalDateTime start, LocalDateTime end);
}
