package com.redhat.cajun.navy.finding.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Victim {

    private String id;

    private String lat;

    private String lon;

    private Integer numberOfPeople;

    private Boolean medicalNeeded;

    private String victimName;

    private String victimPhoneNumber;

    private Long timestamp;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Boolean isMedicalNeeded() {
        return medicalNeeded;
    }

    public void setMedicalNeeded(Boolean medicalNeeded) {
        this.medicalNeeded = medicalNeeded;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getVictimPhoneNumber() {
        return victimPhoneNumber;
    }

    public void setVictimPhoneNumber(String victimPhoneNumber) {
        this.victimPhoneNumber = victimPhoneNumber;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Builder {

        private final Victim victim;

        public Builder() {
            victim = new Victim();
        }

        public Builder(String id) {
            victim = new Victim();
            victim.id = id;
        }

        public Builder lat(String lat) {
            victim.lat = lat;
            return this;
        }

        public Builder lon(String lon) {
            victim.lon = lon;
            return this;
        }

        public Builder numberOfPeople(Integer numberOfPeople) {
            victim.numberOfPeople = numberOfPeople;
            return this;
        }

        public Builder medicalNeeded(Boolean medicalNeeded) {
            victim.medicalNeeded = medicalNeeded;
            return this;
        }

        public Builder victimName(String victimName) {
            victim.victimName = victimName;
            return this;
        }

        public Builder victimPhoneNumber(String victimPhoneNumber) {
            victim.victimPhoneNumber = victimPhoneNumber;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            victim.timestamp = timestamp;
            return this;
        }

        public Builder status(String status) {
            victim.status = status;
            return this;
        }

        public Victim build() {
            return victim;
        }

    }

}
