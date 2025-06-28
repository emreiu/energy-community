package com.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Entity class for the percentage_data table.
 * This table stores the percentage of community and grid usage for each hour.
 */
@Entity
@Table(name = "percentage_data")
public class PercentageData {

    /**
     * The timestamp of the hour (primary key).
     */
    @Id
    private LocalDateTime hour;

    /**
     * Percentage of community energy used (0-100).
     */
    private Double communityDepleted;

    /**
     * Percentage of grid energy used (0-100).
     */
    private Double gridPortion;

    // Getter & Setter
    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public Double getCommunityDepleted() {
        return communityDepleted;
    }

    public void setCommunityDepleted(Double communityDepleted) {
        this.communityDepleted = communityDepleted;
    }

    public Double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(Double gridPortion) {
        this.gridPortion = gridPortion;
    }
}
