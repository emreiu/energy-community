package com.usageservice.repository;

import com.usageservice.entity.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repository interface for accessing the usage_data table.
 * JpaRepository provides standard methods like findById() and save().
 */
@Repository
public interface UsageDataRepository extends JpaRepository<UsageData, LocalDateTime> {
}
