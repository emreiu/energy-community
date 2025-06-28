package com.api.repository;

import com.api.entity.PercentageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for reading data from the percentage_data table.
 * JpaRepository provides standard methods like findAll() and findById().
 */
public interface PercentageDataRepository extends JpaRepository<PercentageData, LocalDateTime> {

    /**
     * Finds the most recent row in the table.
     * @return the latest PercentageData entry
     */
    PercentageData findFirstByOrderByHourDesc();

    /**
     * Finds all rows between the given start and end timestamps.
     * The result is sorted ascending by hour.
     *
     * @param start start timestamp (inclusive)
     * @param end end timestamp (inclusive)
     * @return list of PercentageData entries
     */
    List<PercentageData> findAllByHourBetweenOrderByHourAsc(LocalDateTime start, LocalDateTime end);
}
