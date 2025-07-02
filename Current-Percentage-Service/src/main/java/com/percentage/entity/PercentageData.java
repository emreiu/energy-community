package com.percentage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**

 Entity class for the percentage_data table.
 Stores the calculated percentages of community and grid usage.*/
@Entity
@Table(name = "percentage_data")
public class PercentageData {

    @Id
    private LocalDateTime hour;

    private Double communityDepleted;
    private Double gridPortion;

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
