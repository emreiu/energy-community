package com.percentage.repository;

import com.percentage.entity.PercentageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PercentageDataRepository extends JpaRepository<PercentageData, LocalDateTime> {
}
