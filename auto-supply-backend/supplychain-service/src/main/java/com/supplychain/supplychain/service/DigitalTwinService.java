package com.supplychain.supplychain.service;

import com.supplychain.supplychain.dto.response.DigitalTwinDTO;
import com.supplychain.supplychain.dto.response.DigitalTwinDataResponse;
import com.supplychain.supplychain.dto.response.SupplyChainNodeResponse;
import com.supplychain.supplychain.dto.response.SupplyChainLinkResponse;
import com.supplychain.supplychain.dto.response.SupplyChainEventResponse;
import java.util.List;

public interface DigitalTwinService {
    List<DigitalTwinDTO> getAllDigitalTwins();
    DigitalTwinDTO getDigitalTwinById(Long id);
    DigitalTwinDTO createDigitalTwin(DigitalTwinDTO digitalTwinDTO);
    DigitalTwinDTO updateDigitalTwin(Long id, DigitalTwinDTO digitalTwinDTO);
    void deleteDigitalTwin(Long id);
    List<DigitalTwinDTO> getDigitalTwinsByType(String type);
    List<DigitalTwinDTO> getDigitalTwinsByStatus(String status);
    
    // 新增的数字孪生相关方法
    DigitalTwinDataResponse getDigitalTwinData();
    List<SupplyChainNodeResponse> getAllNodes();
    SupplyChainNodeResponse getNodeById(Long id);
    List<SupplyChainLinkResponse> getAllLinks();
    SupplyChainLinkResponse getLinkById(Long id);
    List<SupplyChainEventResponse> getRealTimeEvents();
    List<SupplyChainEventResponse> getHistoricalData(int days);
    boolean updateNodeStatus(Long id, String status);
    boolean updateLinkStatus(Long id, String status);
}