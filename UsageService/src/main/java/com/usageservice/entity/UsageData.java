package com.usageservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Entity class for the usage_data table.
 * This table stores aggregated energy production and consumption data per hour.
 */
@Entity
@Table(name = "usage_data")
public class UsageData {

    /**
     * The timestamp of the hour (primary key).
     */
    @Id
    private LocalDateTime hour;

    /**
     * Total amount of energy produced by the community in kwh.
     */
    private Double communityProduced = 0.0;

    /**
     * Total amount of energy used by the community in kwh.
     */
    private Double communityUsed = 0.0;

    /**
     * Total amount of energy used from the grid in kwh.
     */
    private Double gridUsed = 0.0;

    // Getter and Setter

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public Double getCommunityProduced() {
        return communityProduced;
    }

    public void setCommunityProduced(Double communityProduced) {
        this.communityProduced = communityProduced;
    }

    public Double getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(Double communityUsed) {
        this.communityUsed = communityUsed;
    }

    public Double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(Double gridUsed) {
        this.gridUsed = gridUsed;
    }
}
