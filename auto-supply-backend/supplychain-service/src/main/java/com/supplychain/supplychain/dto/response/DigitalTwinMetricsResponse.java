package com.supplychain.supplychain.dto.response;

public class DigitalTwinMetricsResponse {
    private double overallHealth;
    private String riskLevel;
    private int totalNodes;
    private int activeNodes;
    private int totalLinks;
    private int activeLinks;
    private double avgLatency;
    private double uptime;

    // Constructors
    public DigitalTwinMetricsResponse() {}

    public DigitalTwinMetricsResponse(double overallHealth, String riskLevel, int totalNodes, int activeNodes,
                                    int totalLinks, int activeLinks, double avgLatency, double uptime) {
        this.overallHealth = overallHealth;
        this.riskLevel = riskLevel;
        this.totalNodes = totalNodes;
        this.activeNodes = activeNodes;
        this.totalLinks = totalLinks;
        this.activeLinks = activeLinks;
        this.avgLatency = avgLatency;
        this.uptime = uptime;
    }

    // Getters and Setters
    public double getOverallHealth() {
        return overallHealth;
    }

    public void setOverallHealth(double overallHealth) {
        this.overallHealth = overallHealth;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public int getActiveNodes() {
        return activeNodes;
    }

    public void setActiveNodes(int activeNodes) {
        this.activeNodes = activeNodes;
    }

    public int getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(int totalLinks) {
        this.totalLinks = totalLinks;
    }

    public int getActiveLinks() {
        return activeLinks;
    }

    public void setActiveLinks(int activeLinks) {
        this.activeLinks = activeLinks;
    }

    public double getAvgLatency() {
        return avgLatency;
    }

    public void setAvgLatency(double avgLatency) {
        this.avgLatency = avgLatency;
    }

    public double getUptime() {
        return uptime;
    }

    public void setUptime(double uptime) {
        this.uptime = uptime;
    }
}