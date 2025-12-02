package com.supplychain.supplychain.dto.response;

import java.time.LocalDateTime;

public class SupplyChainLinkResponse {
    private Long id;
    private Long sourceNodeId;
    private Long targetNodeId;
    private String status;
    private LinkMetricsResponse metrics;
    private CapacityResponse capacity;
    private LocalDateTime lastUpdated;

    // Constructors
    public SupplyChainLinkResponse() {}

    public SupplyChainLinkResponse(Long id, Long sourceNodeId, Long targetNodeId, String status,
                                 LinkMetricsResponse metrics, CapacityResponse capacity, LocalDateTime lastUpdated) {
        this.id = id;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
        this.status = status;
        this.metrics = metrics;
        this.capacity = capacity;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(Long sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public Long getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(Long targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LinkMetricsResponse getMetrics() {
        return metrics;
    }

    public void setMetrics(LinkMetricsResponse metrics) {
        this.metrics = metrics;
    }

    public CapacityResponse getCapacity() {
        return capacity;
    }

    public void setCapacity(CapacityResponse capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Inner classes for nested objects
    public static class LinkMetricsResponse {
        private double bandwidth;
        private double latency;
        private double packetLoss;
        private double utilization;

        public LinkMetricsResponse() {}

        public LinkMetricsResponse(double bandwidth, double latency, double packetLoss, double utilization) {
            this.bandwidth = bandwidth;
            this.latency = latency;
            this.packetLoss = packetLoss;
            this.utilization = utilization;
        }

        public double getBandwidth() {
            return bandwidth;
        }

        public void setBandwidth(double bandwidth) {
            this.bandwidth = bandwidth;
        }

        public double getLatency() {
            return latency;
        }

        public void setLatency(double latency) {
            this.latency = latency;
        }

        public double getPacketLoss() {
            return packetLoss;
        }

        public void setPacketLoss(double packetLoss) {
            this.packetLoss = packetLoss;
        }

        public double getUtilization() {
            return utilization;
        }

        public void setUtilization(double utilization) {
            this.utilization = utilization;
        }
    }

    public static class CapacityResponse {
        private double max;
        private double current;
        private String unit;

        public CapacityResponse() {}

        public CapacityResponse(double max, double current, String unit) {
            this.max = max;
            this.current = current;
            this.unit = unit;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getCurrent() {
            return current;
        }

        public void setCurrent(double current) {
            this.current = current;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}