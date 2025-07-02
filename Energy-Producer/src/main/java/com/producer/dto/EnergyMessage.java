package com.producer.dto;

/**

 DTO for energy messages sent to RabbitMQ.*/
public class EnergyMessage {

    private String type;          // PRODUCER
    private String association;   // COMMUNITY
    private double kwh;
    private String datetime;      // ISO timestamp

    public EnergyMessage() {
    }

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