package com.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Entity class for the usage_data table.
 * This table stores the produced and used energy values per hour.
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
     * kWh produced by the community.
     */
    private Double communityProduced;

    /**
     * kWh used by the community.
     */
    private Double communityUsed;

    /**
     * kWh delivered by the grid.
     */
    private Double gridUsed;

    // Getter & Setter
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
