package com.percentage.repository;

import com.percentage.entity.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UsageDataRepository extends JpaRepository<UsageData, LocalDateTime> {
}