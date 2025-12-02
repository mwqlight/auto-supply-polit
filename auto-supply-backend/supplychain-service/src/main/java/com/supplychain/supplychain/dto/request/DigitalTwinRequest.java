package com.supplychain.supplychain.dto.request;

import java.time.LocalDateTime;

public class DigitalTwinRequest {
    private String twinName;
    private String twinType;
    private String status;
    private String location;

    // Constructors
    public DigitalTwinRequest() {}

    public DigitalTwinRequest(String twinName, String twinType, String status, String location) {
        this.twinName = twinName;
        this.twinType = twinType;
        this.status = status;
        this.location = location;
    }

    // Getters and Setters
    public String getTwinName() {
        return twinName;
    }

    public void setTwinName(String twinName) {
        this.twinName = twinName;
    }

    public String getTwinType() {
        return twinType;
    }

    public void setTwinType(String twinType) {
        this.twinType = twinType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}