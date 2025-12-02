package com.supplychain.supplychain.dto.response;

import java.util.List;

public class DigitalTwinResponse {
    private Metadata metadata;
    private NetworkStructure networkStructure;
    private RealTimeInsights realTimeInsights;
    private List<AIInsight> aiInsights;
    private VisualizationHints visualizationHints;
    
    // Constructors
    public DigitalTwinResponse() {
    }
    
    // Getters and setters
    public Metadata getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    
    public NetworkStructure getNetworkStructure() {
        return networkStructure;
    }
    
    public void setNetworkStructure(NetworkStructure networkStructure) {
        this.networkStructure = networkStructure;
    }
    
    public RealTimeInsights getRealTimeInsights() {
        return realTimeInsights;
    }
    
    public void setRealTimeInsights(RealTimeInsights realTimeInsights) {
        this.realTimeInsights = realTimeInsights;
    }
    
    public List<AIInsight> getAiInsights() {
        return aiInsights;
    }
    
    public void setAiInsights(List<AIInsight> aiInsights) {
        this.aiInsights = aiInsights;
    }
    
    public VisualizationHints getVisualizationHints() {
        return visualizationHints;
    }
    
    public void setVisualizationHints(VisualizationHints visualizationHints) {
        this.visualizationHints = visualizationHints;
    }
    
    // Inner classes for the complex structure
    public static class Metadata {
        private String generatedAt;
        private String dataSource;
        private int refreshInterval;
        private String perspective;
        private int nodeCount;
        private int edgeCount;
        private int riskAlertCount;
        
        // Getters and setters
        public String getGeneratedAt() {
            return generatedAt;
        }
        
        public void setGeneratedAt(String generatedAt) {
            this.generatedAt = generatedAt;
        }
        
        public String getDataSource() {
            return dataSource;
        }
        
        public void setDataSource(String dataSource) {
            this.dataSource = dataSource;
        }
        
        public int getRefreshInterval() {
            return refreshInterval;
        }
        
        public void setRefreshInterval(int refreshInterval) {
            this.refreshInterval = refreshInterval;
        }
        
        public String getPerspective() {
            return perspective;
        }
        
        public void setPerspective(String perspective) {
            this.perspective = perspective;
        }
        
        public int getNodeCount() {
            return nodeCount;
        }
        
        public void setNodeCount(int nodeCount) {
            this.nodeCount = nodeCount;
        }
        
        public int getEdgeCount() {
            return edgeCount;
        }
        
        public void setEdgeCount(int edgeCount) {
            this.edgeCount = edgeCount;
        }
        
        public int getRiskAlertCount() {
            return riskAlertCount;
        }
        
        public void setRiskAlertCount(int riskAlertCount) {
            this.riskAlertCount = riskAlertCount;
        }
    }
    
    public static class NetworkStructure {
        private List<Node> nodes;
        private List<Edge> edges;
        
        // Getters and setters
        public List<Node> getNodes() {
            return nodes;
        }
        
        public void setNodes(List<Node> nodes) {
            this.nodes = nodes;
        }
        
        public List<Edge> getEdges() {
            return edges;
        }
        
        public void setEdges(List<Edge> edges) {
            this.edges = edges;
        }
    }
    
    public static class Node {
        private String id;
        private String type;
        private String name;
        private Location location;
        private NodeMetrics metrics;
        private String status;
        private List<String> connections;
        private List<Alert> alerts;
        
        // Getters and setters
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Location getLocation() {
            return location;
        }
        
        public void setLocation(Location location) {
            this.location = location;
        }
        
        public NodeMetrics getMetrics() {
            return metrics;
        }
        
        public void setMetrics(NodeMetrics metrics) {
            this.metrics = metrics;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public List<String> getConnections() {
            return connections;
        }
        
        public void setConnections(List<String> connections) {
            this.connections = connections;
        }
        
        public List<Alert> getAlerts() {
            return alerts;
        }
        
        public void setAlerts(List<Alert> alerts) {
            this.alerts = alerts;
        }
    }
    
    public static class Edge {
        private String id;
        private String source;
        private String target;
        private String type;
        private EdgeMetrics metrics;
        private String status;
        private List<String> materialTypes;
        
        // Getters and setters
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getSource() {
            return source;
        }
        
        public void setSource(String source) {
            this.source = source;
        }
        
        public String getTarget() {
            return target;
        }
        
        public void setTarget(String target) {
            this.target = target;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public EdgeMetrics getMetrics() {
            return metrics;
        }
        
        public void setMetrics(EdgeMetrics metrics) {
            this.metrics = metrics;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public List<String> getMaterialTypes() {
            return materialTypes;
        }
        
        public void setMaterialTypes(List<String> materialTypes) {
            this.materialTypes = materialTypes;
        }
    }
    
    public static class Location {
        private String country;
        private String city;
        private List<Double> coordinates;
        private String facilityType;
        
        // Getters and setters
        public String getCountry() {
            return country;
        }
        
        public void setCountry(String country) {
            this.country = country;
        }
        
        public String getCity() {
            return city;
        }
        
        public void setCity(String city) {
            this.city = city;
        }
        
        public List<Double> getCoordinates() {
            return coordinates;
        }
        
        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
        
        public String getFacilityType() {
            return facilityType;
        }
        
        public void setFacilityType(String facilityType) {
            this.facilityType = facilityType;
        }
    }
    
    public static class NodeMetrics {
        private int riskScore;
        private double performanceScore;
        private double capacityUtilization;
        private double onTimeDelivery;
        private double qualityRating;
        private double energyConsumption;
        private double carbonEmission;
        
        // Getters and setters
        public int getRiskScore() {
            return riskScore;
        }
        
        public void setRiskScore(int riskScore) {
            this.riskScore = riskScore;
        }
        
        public double getPerformanceScore() {
            return performanceScore;
        }
        
        public void setPerformanceScore(double performanceScore) {
            this.performanceScore = performanceScore;
        }
        
        public double getCapacityUtilization() {
            return capacityUtilization;
        }
        
        public void setCapacityUtilization(double capacityUtilization) {
            this.capacityUtilization = capacityUtilization;
        }
        
        public double getOnTimeDelivery() {
            return onTimeDelivery;
        }
        
        public void setOnTimeDelivery(double onTimeDelivery) {
            this.onTimeDelivery = onTimeDelivery;
        }
        
        public double getQualityRating() {
            return qualityRating;
        }
        
        public void setQualityRating(double qualityRating) {
            this.qualityRating = qualityRating;
        }
        
        public double getEnergyConsumption() {
            return energyConsumption;
        }
        
        public void setEnergyConsumption(double energyConsumption) {
            this.energyConsumption = energyConsumption;
        }
        
        public double getCarbonEmission() {
            return carbonEmission;
        }
        
        public void setCarbonEmission(double carbonEmission) {
            this.carbonEmission = carbonEmission;
        }
    }
    
    public static class EdgeMetrics {
        private int volume;
        private double value;
        private double leadTime;
        private double reliability;
        private double flowRate;
        
        // Getters and setters
        public int getVolume() {
            return volume;
        }
        
        public void setVolume(int volume) {
            this.volume = volume;
        }
        
        public double getValue() {
            return value;
        }
        
        public void setValue(double value) {
            this.value = value;
        }
        
        public double getLeadTime() {
            return leadTime;
        }
        
        public void setLeadTime(double leadTime) {
            this.leadTime = leadTime;
        }
        
        public double getReliability() {
            return reliability;
        }
        
        public void setReliability(double reliability) {
            this.reliability = reliability;
        }
        
        public double getFlowRate() {
            return flowRate;
        }
        
        public void setFlowRate(double flowRate) {
            this.flowRate = flowRate;
        }
    }
    
    public static class Alert {
        private String type;
        private String level;
        private String message;
        
        // Getters and setters
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getLevel() {
            return level;
        }
        
        public void setLevel(String level) {
            this.level = level;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    public static class RealTimeInsights {
        private List<Hotspot> hotspots;
        private List<DataStream> dataStreams;
        
        // Getters and setters
        public List<Hotspot> getHotspots() {
            return hotspots;
        }
        
        public void setHotspots(List<Hotspot> hotspots) {
            this.hotspots = hotspots;
        }
        
        public List<DataStream> getDataStreams() {
            return dataStreams;
        }
        
        public void setDataStreams(List<DataStream> dataStreams) {
            this.dataStreams = dataStreams;
        }
    }
    
    public static class Hotspot {
        private List<Double> location;
        private double intensity;
        private String type;
        private List<String> affectedProducts;
        private String estimatedImpact;
        
        // Getters and setters
        public List<Double> getLocation() {
            return location;
        }
        
        public void setLocation(List<Double> location) {
            this.location = location;
        }
        
        public double getIntensity() {
            return intensity;
        }
        
        public void setIntensity(double intensity) {
            this.intensity = intensity;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public List<String> getAffectedProducts() {
            return affectedProducts;
        }
        
        public void setAffectedProducts(List<String> affectedProducts) {
            this.affectedProducts = affectedProducts;
        }
        
        public String getEstimatedImpact() {
            return estimatedImpact;
        }
        
        public void setEstimatedImpact(String estimatedImpact) {
            this.estimatedImpact = estimatedImpact;
        }
    }
    
    public static class DataStream {
        private String streamId;
        private String location;
        private double currentValue;
        private Threshold threshold;
        private String status;
        
        // Getters and setters
        public String getStreamId() {
            return streamId;
        }
        
        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }
        
        public String getLocation() {
            return location;
        }
        
        public void setLocation(String location) {
            this.location = location;
        }
        
        public double getCurrentValue() {
            return currentValue;
        }
        
        public void setCurrentValue(double currentValue) {
            this.currentValue = currentValue;
        }
        
        public Threshold getThreshold() {
            return threshold;
        }
        
        public void setThreshold(Threshold threshold) {
            this.threshold = threshold;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
    }
    
    public static class Threshold {
        private double min;
        private double max;
        
        // Getters and setters
        public double getMin() {
            return min;
        }
        
        public void setMin(double min) {
            this.min = min;
        }
        
        public double getMax() {
            return max;
        }
        
        public void setMax(double max) {
            this.max = max;
        }
    }
    
    public static class AIInsight {
        private String id;
        private String title;
        private String description;
        private double confidence;
        private List<RecommendedAction> recommendedActions;
        private List<SimulationResult> simulationResults;
        
        // Getters and setters
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
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
        
        public double getConfidence() {
            return confidence;
        }
        
        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }
        
        public List<RecommendedAction> getRecommendedActions() {
            return recommendedActions;
        }
        
        public void setRecommendedActions(List<RecommendedAction> recommendedActions) {
            this.recommendedActions = recommendedActions;
        }
        
        public List<SimulationResult> getSimulationResults() {
            return simulationResults;
        }
        
        public void setSimulationResults(List<SimulationResult> simulationResults) {
            this.simulationResults = simulationResults;
        }
    }
    
    public static class RecommendedAction {
        private String action;
        private String target;
        private ActionParameters parameters;
        private String estimatedImpact;
        
        // Getters and setters
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
        
        public String getTarget() {
            return target;
        }
        
        public void setTarget(String target) {
            this.target = target;
        }
        
        public ActionParameters getParameters() {
            return parameters;
        }
        
        public void setParameters(ActionParameters parameters) {
            this.parameters = parameters;
        }
        
        public String getEstimatedImpact() {
            return estimatedImpact;
        }
        
        public void setEstimatedImpact(String estimatedImpact) {
            this.estimatedImpact = estimatedImpact;
        }
    }
    
    public static class ActionParameters {
        private String shiftPattern;
        private String duration;
        
        // Getters and setters
        public String getShiftPattern() {
            return shiftPattern;
        }
        
        public void setShiftPattern(String shiftPattern) {
            this.shiftPattern = shiftPattern;
        }
        
        public String getDuration() {
            return duration;
        }
        
        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
    
    public static class SimulationResult {
        private String scenario;
        private SimulationMetrics metrics;
        
        // Getters and setters
        public String getScenario() {
            return scenario;
        }
        
        public void setScenario(String scenario) {
            this.scenario = scenario;
        }
        
        public SimulationMetrics getMetrics() {
            return metrics;
        }
        
        public void setMetrics(SimulationMetrics metrics) {
            this.metrics = metrics;
        }
    }
    
    public static class SimulationMetrics {
        private int delayedOrders;
        private int costImpact;
        private int customerSatisfaction;
        
        // Getters and setters
        public int getDelayedOrders() {
            return delayedOrders;
        }
        
        public void setDelayedOrders(int delayedOrders) {
            this.delayedOrders = delayedOrders;
        }
        
        public int getCostImpact() {
            return costImpact;
        }
        
        public void setCostImpact(int costImpact) {
            this.costImpact = costImpact;
        }
        
        public int getCustomerSatisfaction() {
            return customerSatisfaction;
        }
        
        public void setCustomerSatisfaction(int customerSatisfaction) {
            this.customerSatisfaction = customerSatisfaction;
        }
    }
    
    public static class VisualizationHints {
        private ColorSchemes colorSchemes;
        private NodeSizes nodeSizes;
        private AnimationSettings animationSettings;
        
        // Getters and setters
        public ColorSchemes getColorSchemes() {
            return colorSchemes;
        }
        
        public void setColorSchemes(ColorSchemes colorSchemes) {
            this.colorSchemes = colorSchemes;
        }
        
        public NodeSizes getNodeSizes() {
            return nodeSizes;
        }
        
        public void setNodeSizes(NodeSizes nodeSizes) {
            this.nodeSizes = nodeSizes;
        }
        
        public AnimationSettings getAnimationSettings() {
            return animationSettings;
        }
        
        public void setAnimationSettings(AnimationSettings animationSettings) {
            this.animationSettings = animationSettings;
        }
    }
    
    public static class ColorSchemes {
        private RiskLevelColors riskLevel;
        private FlowVolumeColors flowVolume;
        
        // Getters and setters
        public RiskLevelColors getRiskLevel() {
            return riskLevel;
        }
        
        public void setRiskLevel(RiskLevelColors riskLevel) {
            this.riskLevel = riskLevel;
        }
        
        public FlowVolumeColors getFlowVolume() {
            return flowVolume;
        }
        
        public void setFlowVolume(FlowVolumeColors flowVolume) {
            this.flowVolume = flowVolume;
        }
    }
    
    public static class RiskLevelColors {
        private String low;
        private String medium;
        private String high;
        
        // Getters and setters
        public String getLow() {
            return low;
        }
        
        public void setLow(String low) {
            this.low = low;
        }
        
        public String getMedium() {
            return medium;
        }
        
        public void setMedium(String medium) {
            this.medium = medium;
        }
        
        public String getHigh() {
            return high;
        }
        
        public void setHigh(String high) {
            this.high = high;
        }
    }
    
    public static class FlowVolumeColors {
        private String low;
        private String medium;
        private String high;
        
        // Getters and setters
        public String getLow() {
            return low;
        }
        
        public void setLow(String low) {
            this.low = low;
        }
        
        public String getMedium() {
            return medium;
        }
        
        public void setMedium(String medium) {
            this.medium = medium;
        }
        
        public String getHigh() {
            return high;
        }
        
        public void setHigh(String high) {
            this.high = high;
        }
    }
    
    public static class NodeSizes {
        private int supplier;
        private int factory;
        private int warehouse;
        private int customer;
        
        // Getters and setters
        public int getSupplier() {
            return supplier;
        }
        
        public void setSupplier(int supplier) {
            this.supplier = supplier;
        }
        
        public int getFactory() {
            return factory;
        }
        
        public void setFactory(int factory) {
            this.factory = factory;
        }
        
        public int getWarehouse() {
            return warehouse;
        }
        
        public void setWarehouse(int warehouse) {
            this.warehouse = warehouse;
        }
        
        public int getCustomer() {
            return customer;
        }
        
        public void setCustomer(int customer) {
            this.customer = customer;
        }
    }
    
    public static class AnimationSettings {
        private double pulseSpeed;
        private double flowSpeed;
        private double highlightIntensity;
        
        // Getters and setters
        public double getPulseSpeed() {
            return pulseSpeed;
        }
        
        public void setPulseSpeed(double pulseSpeed) {
            this.pulseSpeed = pulseSpeed;
        }
        
        public double getFlowSpeed() {
            return flowSpeed;
        }
        
        public void setFlowSpeed(double flowSpeed) {
            this.flowSpeed = flowSpeed;
        }
        
        public double getHighlightIntensity() {
            return highlightIntensity;
        }
        
        public void setHighlightIntensity(double highlightIntensity) {
            this.highlightIntensity = highlightIntensity;
        }
    }
}