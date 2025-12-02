package com.supplychain.supplychain.dto.response;

import java.util.List;

public class DigitalTwinDataResponse {
    private List<SupplyChainNodeResponse> nodes;
    private List<SupplyChainLinkResponse> links;
    private DigitalTwinMetricsResponse metrics;
    private List<SupplyChainEventResponse> events;

    // Constructors
    public DigitalTwinDataResponse() {}

    public DigitalTwinDataResponse(List<SupplyChainNodeResponse> nodes, List<SupplyChainLinkResponse> links,
                                DigitalTwinMetricsResponse metrics, List<SupplyChainEventResponse> events) {
        this.nodes = nodes;
        this.links = links;
        this.metrics = metrics;
        this.events = events;
    }

    // Getters and Setters
    public List<SupplyChainNodeResponse> getNodes() {
        return nodes;
    }

    public void setNodes(List<SupplyChainNodeResponse> nodes) {
        this.nodes = nodes;
    }

    public List<SupplyChainLinkResponse> getLinks() {
        return links;
    }

    public void setLinks(List<SupplyChainLinkResponse> links) {
        this.links = links;
    }

    public DigitalTwinMetricsResponse getMetrics() {
        return metrics;
    }

    public void setMetrics(DigitalTwinMetricsResponse metrics) {
        this.metrics = metrics;
    }

    public List<SupplyChainEventResponse> getEvents() {
        return events;
    }

    public void setEvents(List<SupplyChainEventResponse> events) {
        this.events = events;
    }
}