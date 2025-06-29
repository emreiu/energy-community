package com.usageservice.dto;

/**
 * DTO representing an energy message sent by a producer or user.
 * This class is used for mapping incoming JSON messages from RabbitMQ.
 */
public class EnergyMessage {

    /**
     * The type of the message (PRODUCER or USER).
     */
    private String type;

    /**
     * The association of the message (example COMMUNITY).
     */
    private String association;

    /**
     * The amount of energy in kwh.
     */
    private double kwh;

    /**
     * The timestamp of the energy measurement.
     * Example: "2025-01-10T14:33:00"
     */
    private String datetime;

    /**
     * Default constructor required for JSON deserialization.
     */
    public EnergyMessage() {
    }

    // Getter and Setter

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
