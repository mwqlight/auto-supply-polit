package com.supplychain.supplychain.service.impl;

import com.supplychain.supplychain.dto.response.DigitalTwinDTO;
import com.supplychain.supplychain.dto.response.DigitalTwinDataResponse;
import com.supplychain.supplychain.dto.response.SupplyChainNodeResponse;
import com.supplychain.supplychain.dto.response.SupplyChainLinkResponse;
import com.supplychain.supplychain.dto.response.SupplyChainEventResponse;
import com.supplychain.supplychain.dto.response.DigitalTwinMetricsResponse;
import com.supplychain.supplychain.entity.DigitalTwin;
import com.supplychain.supplychain.repository.DigitalTwinRepository;
import com.supplychain.supplychain.service.DigitalTwinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

@Service
public class DigitalTwinServiceImpl implements DigitalTwinService {

    @Autowired
    private DigitalTwinRepository digitalTwinRepository;

    @Override
    public List<DigitalTwinDTO> getAllDigitalTwins() {
        return digitalTwinRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DigitalTwinDTO getDigitalTwinById(Long id) {
        return digitalTwinRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public DigitalTwinDTO createDigitalTwin(DigitalTwinDTO digitalTwinDTO) {
        DigitalTwin digitalTwin = convertToEntity(digitalTwinDTO);
        DigitalTwin savedDigitalTwin = digitalTwinRepository.save(digitalTwin);
        return convertToDTO(savedDigitalTwin);
    }

    @Override
    public DigitalTwinDTO updateDigitalTwin(Long id, DigitalTwinDTO digitalTwinDTO) {
        return digitalTwinRepository.findById(id)
                .map(existingDigitalTwin -> {
                    existingDigitalTwin.setTwinName(digitalTwinDTO.getTwinName());
                    existingDigitalTwin.setTwinType(digitalTwinDTO.getTwinType());
                    existingDigitalTwin.setStatus(digitalTwinDTO.getStatus());
                    existingDigitalTwin.setLocation(digitalTwinDTO.getLocation());
                    DigitalTwin updatedDigitalTwin = digitalTwinRepository.save(existingDigitalTwin);
                    return convertToDTO(updatedDigitalTwin);
                })
                .orElse(null);
    }

    @Override
    public void deleteDigitalTwin(Long id) {
        digitalTwinRepository.deleteById(id);
    }

    @Override
    public List<DigitalTwinDTO> getDigitalTwinsByType(String type) {
        return digitalTwinRepository.findByTwinType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DigitalTwinDTO> getDigitalTwinsByStatus(String status) {
        return digitalTwinRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DigitalTwinDataResponse getDigitalTwinData() {
        // 创建模拟数据
        List<SupplyChainNodeResponse> nodes = generateMockNodes();
        List<SupplyChainLinkResponse> links = generateMockLinks();
        DigitalTwinMetricsResponse metrics = generateMockMetrics();
        List<SupplyChainEventResponse> events = generateMockEvents();
        
        return new DigitalTwinDataResponse(nodes, links, metrics, events);
    }

    @Override
    public List<SupplyChainNodeResponse> getAllNodes() {
        return generateMockNodes();
    }

    @Override
    public SupplyChainNodeResponse getNodeById(Long id) {
        // 返回模拟节点数据
        SupplyChainNodeResponse.LocationResponse location = new SupplyChainNodeResponse.LocationResponse(
            31.2304, 121.4737, "上海市浦东新区");
            
        SupplyChainNodeResponse.NodeMetricsResponse metrics = new SupplyChainNodeResponse.NodeMetricsResponse(
            95.5, 12.3, 99.2, 98.7);
            
        SupplyChainNodeResponse.CapacityResponse capacity = new SupplyChainNodeResponse.CapacityResponse(
            10000, 8750, "units");
            
        return new SupplyChainNodeResponse(id, "上海制造中心", "manufacturer", "normal", 
                                         location, metrics, capacity, LocalDateTime.now());
    }

    @Override
    public List<SupplyChainLinkResponse> getAllLinks() {
        return generateMockLinks();
    }

    @Override
    public SupplyChainLinkResponse getLinkById(Long id) {
        // 返回模拟链接数据
        SupplyChainLinkResponse.LinkMetricsResponse metrics = new SupplyChainLinkResponse.LinkMetricsResponse(
            1000, 15.2, 0.02, 78.5);
            
        SupplyChainLinkResponse.CapacityResponse capacity = new SupplyChainLinkResponse.CapacityResponse(
            5000, 3900, "units/day");
            
        return new SupplyChainLinkResponse(id, 1L, 2L, "normal", metrics, capacity, LocalDateTime.now());
    }

    @Override
    public List<SupplyChainEventResponse> getRealTimeEvents() {
        return generateMockEvents();
    }

    @Override
    public List<SupplyChainEventResponse> getHistoricalData(int days) {
        return generateMockEvents();
    }

    @Override
    public boolean updateNodeStatus(Long id, String status) {
        // 在实际应用中，这里会更新数据库中的节点状态
        // 现在我们只是模拟这个操作
        return true;
    }

    @Override
    public boolean updateLinkStatus(Long id, String status) {
        // 在实际应用中，这里会更新数据库中的链接状态
        // 现在我们只是模拟这个操作
        return true;
    }

    private DigitalTwinDTO convertToDTO(DigitalTwin digitalTwin) {
        return new DigitalTwinDTO(
                digitalTwin.getId(),
                digitalTwin.getTwinName(),
                digitalTwin.getTwinType(),
                digitalTwin.getStatus(),
                digitalTwin.getLocation(),
                digitalTwin.getCreatedAt(),
                digitalTwin.getUpdatedAt()
        );
    }

    private DigitalTwin convertToEntity(DigitalTwinDTO digitalTwinDTO) {
        DigitalTwin digitalTwin = new DigitalTwin();
        digitalTwin.setId(digitalTwinDTO.getId());
        digitalTwin.setTwinName(digitalTwinDTO.getTwinName());
        digitalTwin.setTwinType(digitalTwinDTO.getTwinType());
        digitalTwin.setStatus(digitalTwinDTO.getStatus());
        digitalTwin.setLocation(digitalTwinDTO.getLocation());
        return digitalTwin;
    }
    
    // 生成模拟节点数据
    private List<SupplyChainNodeResponse> generateMockNodes() {
        List<SupplyChainNodeResponse> nodes = new ArrayList<>();
        Random random = new Random();
        
        String[] nodeTypes = {"supplier", "manufacturer", "distributor", "retailer", "customer"};
        String[] statuses = {"normal", "warning", "error", "offline"};
        String[] addresses = {"上海市浦东新区", "深圳市南山区", "北京市朝阳区", "广州市天河区", "杭州市西湖区"};
        
        for (int i = 1; i <= 10; i++) {
            SupplyChainNodeResponse.LocationResponse location = new SupplyChainNodeResponse.LocationResponse(
                30 + random.nextDouble() * 10, 110 + random.nextDouble() * 20, addresses[random.nextInt(addresses.length)]);
                
            SupplyChainNodeResponse.NodeMetricsResponse metrics = new SupplyChainNodeResponse.NodeMetricsResponse(
                80 + random.nextDouble() * 20, 5 + random.nextDouble() * 20, 
                90 + random.nextDouble() * 10, 90 + random.nextDouble() * 10);
                
            SupplyChainNodeResponse.CapacityResponse capacity = new SupplyChainNodeResponse.CapacityResponse(
                5000 + random.nextDouble() * 10000, 3000 + random.nextDouble() * 7000, "units");
                
            nodes.add(new SupplyChainNodeResponse((long)i, "节点-" + i, 
                       nodeTypes[random.nextInt(nodeTypes.length)], 
                       statuses[random.nextInt(statuses.length)], 
                       location, metrics, capacity, LocalDateTime.now()));
        }
        
        return nodes;
    }
    
    // 生成模拟链接数据
    private List<SupplyChainLinkResponse> generateMockLinks() {
        List<SupplyChainLinkResponse> links = new ArrayList<>();
        Random random = new Random();
        
        String[] statuses = {"normal", "warning", "error", "offline"};
        
        for (int i = 1; i <= 8; i++) {
            SupplyChainLinkResponse.LinkMetricsResponse metrics = new SupplyChainLinkResponse.LinkMetricsResponse(
                500 + random.nextDouble() * 1000, 10 + random.nextDouble() * 30, 
                random.nextDouble() * 0.1, 60 + random.nextDouble() * 40);
                
            SupplyChainLinkResponse.CapacityResponse capacity = new SupplyChainLinkResponse.CapacityResponse(
                2000 + random.nextDouble() * 5000, 1000 + random.nextDouble() * 4000, "units/day");
                
            links.add(new SupplyChainLinkResponse((long)i, (long)i, (long)(i+1), 
                       statuses[random.nextInt(statuses.length)], 
                       metrics, capacity, LocalDateTime.now()));
        }
        
        return links;
    }
    
    // 生成模拟指标数据
    private DigitalTwinMetricsResponse generateMockMetrics() {
        Random random = new Random();
        return new DigitalTwinMetricsResponse(
            85 + random.nextDouble() * 15, // overallHealth
            random.nextInt(3) == 0 ? "high" : (random.nextInt(2) == 0 ? "medium" : "low"), // riskLevel
            10, // totalNodes
            8,  // activeNodes
            8,  // totalLinks
            7,  // activeLinks
            15 + random.nextDouble() * 20, // avgLatency
            95 + random.nextDouble() * 5   // uptime
        );
    }
    
    // 生成模拟事件数据
    private List<SupplyChainEventResponse> generateMockEvents() {
        List<SupplyChainEventResponse> events = new ArrayList<>();
        Random random = new Random();
        
        String[] types = {"disruption", "delay", "quality", "capacity", "maintenance"};
        String[] severities = {"low", "medium", "high", "critical"};
        String[] titles = {"订单确认", "库存预警", "运输完成", "质量异常", "生产计划"};
        String[] descriptions = {
            "客户订单#ORD-2023-001245已确认",
            "原材料A库存低于安全水位",
            "货物已送达北京配送中心",
            "批次#LOT-B20230401检测不合格",
            "新产品线生产计划已下达"
        };
        
        for (int i = 1; i <= 5; i++) {
            events.add(new SupplyChainEventResponse(
                (long)i,
                types[random.nextInt(types.length)],
                severities[random.nextInt(severities.length)],
                (long)(random.nextInt(10) + 1),
                null,
                titles[random.nextInt(titles.length)],
                descriptions[random.nextInt(descriptions.length)],
                LocalDateTime.now().minusHours(random.nextInt(24)),
                random.nextBoolean()
            ));
        }
        
        return events;
    }
}