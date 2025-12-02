package com.supplychain.supplychain.dto.response;

import java.time.LocalDateTime;

public class SupplyChainEventResponse {
    private Long id;
    private String type;
    private String severity;
    private Long nodeId;
    private Long linkId;
    private String title;
    private String description;
    private LocalDateTime timestamp;
    private boolean resolved;

    // Constructors
    public SupplyChainEventResponse() {}

    public SupplyChainEventResponse(Long id, String type, String severity, Long nodeId, Long linkId,
                                 String title, String description, LocalDateTime timestamp, boolean resolved) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.nodeId = nodeId;
        this.linkId = linkId;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.resolved = resolved;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}