package com.supplychain.supplychain.dto.response;

import java.time.LocalDateTime;

public class SupplyChainNodeResponse {
    private Long id;
    private String name;
    private String type;
    private String status;
    private LocationResponse location;
    private NodeMetricsResponse metrics;
    private CapacityResponse capacity;
    private LocalDateTime lastUpdated;

    // Constructors
    public SupplyChainNodeResponse() {}

    public SupplyChainNodeResponse(Long id, String name, String type, String status, 
                                 LocationResponse location, NodeMetricsResponse metrics,
                                 CapacityResponse capacity, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.location = location;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public NodeMetricsResponse getMetrics() {
        return metrics;
    }

    public void setMetrics(NodeMetricsResponse metrics) {
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
    public static class LocationResponse {
        private double lat;
        private double lng;
        private String address;

        public LocationResponse() {}

        public LocationResponse(double lat, double lng, String address) {
            this.lat = lat;
            this.lng = lng;
            this.address = address;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class NodeMetricsResponse {
        private double throughput;
        private double latency;
        private double availability;
        private double quality;

        public NodeMetricsResponse() {}

        public NodeMetricsResponse(double throughput, double latency, double availability, double quality) {
            this.throughput = throughput;
            this.latency = latency;
            this.availability = availability;
            this.quality = quality;
        }

        public double getThroughput() {
            return throughput;
        }

        public void setThroughput(double throughput) {
            this.throughput = throughput;
        }

        public double getLatency() {
            return latency;
        }

        public void setLatency(double latency) {
            this.latency = latency;
        }

        public double getAvailability() {
            return availability;
        }

        public void setAvailability(double availability) {
            this.availability = availability;
        }

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
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