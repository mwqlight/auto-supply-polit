package com.supplychain.supplychain.dto.response;

import java.time.LocalDateTime;

public class DigitalTwinDTO {
    private Long id;
    private String twinName;
    private String twinType;
    private String status;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public DigitalTwinDTO() {}

    public DigitalTwinDTO(Long id, String twinName, String twinType, String status, String location, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.twinName = twinName;
        this.twinType = twinType;
        this.status = status;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}