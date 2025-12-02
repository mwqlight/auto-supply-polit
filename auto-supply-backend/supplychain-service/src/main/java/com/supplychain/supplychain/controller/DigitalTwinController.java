package com.supplychain.supplychain.controller;

import com.supplychain.supplychain.dto.response.ApiResponse;
import com.supplychain.supplychain.dto.response.DigitalTwinResponse;
import com.supplychain.supplychain.dto.response.DigitalTwinDataResponse;
import com.supplychain.supplychain.dto.response.DigitalTwinDTO;
import com.supplychain.supplychain.dto.response.SupplyChainNodeResponse;
import com.supplychain.supplychain.dto.response.SupplyChainLinkResponse;
import com.supplychain.supplychain.dto.response.SupplyChainEventResponse;
import com.supplychain.supplychain.service.DigitalTwinService;
import com.supplychain.supplychain.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/supplychain")
@Tag(name = "Supplychain Digital Twin", description = "供应链数字孪生API")
public class DigitalTwinController {
    
    @Autowired
    private DigitalTwinService digitalTwinService;
    
    @GetMapping("/digital-twin")
    @Operation(summary = "获取供应链数字孪生数据", description = "获取实时的供应链网络拓扑和状态信息")
    public ApiResponse<DigitalTwinResponse> getDigitalTwin() {
        // Implementation will be added later
        return ApiResponse.success(new DigitalTwinResponse());
    }
    
    @GetMapping("/digital-twin/data")
    @Operation(summary = "获取数字孪生完整数据", description = "获取包含节点、链接、指标和事件的完整数字孪生数据")
    public ApiResponse<DigitalTwinDataResponse> getDigitalTwinData() {
        try {
            DigitalTwinDataResponse data = digitalTwinService.getDigitalTwinData();
            return ApiResponse.success(data);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取数字孪生数据失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/digital-twin/nodes")
    @Operation(summary = "获取所有供应链节点", description = "获取所有供应链节点信息")
    public ApiResponse<List<SupplyChainNodeResponse>> getAllNodes() {
        try {
            List<SupplyChainNodeResponse> nodes = digitalTwinService.getAllNodes();
            return ApiResponse.success(nodes);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取节点列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/digital-twin/nodes/{id}")
    @Operation(summary = "根据ID获取节点详情", description = "根据节点ID获取详细信息")
    public ApiResponse<SupplyChainNodeResponse> getNodeById(@PathVariable Long id) {
        try {
            SupplyChainNodeResponse node = digitalTwinService.getNodeById(id);
            if (node != null) {
                return ApiResponse.success(node);
            } else {
                return ApiResponse.error(404, "节点不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "获取节点详情失败: " + e.getMessage());
        }
    }

    @GetMapping("/digital-twin/links")
    @Operation(summary = "获取所有供应链链接", description = "获取所有供应链链接信息")
    public ApiResponse<List<SupplyChainLinkResponse>> getAllLinks() {
        try {
            List<SupplyChainLinkResponse> links = digitalTwinService.getAllLinks();
            return ApiResponse.success(links);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取链接列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/digital-twin/links/{id}")
    @Operation(summary = "根据ID获取链接详情", description = "根据链接ID获取详细信息")
    public ApiResponse<SupplyChainLinkResponse> getLinkById(@PathVariable Long id) {
        try {
            SupplyChainLinkResponse link = digitalTwinService.getLinkById(id);
            if (link != null) {
                return ApiResponse.success(link);
            } else {
                return ApiResponse.error(404, "链接不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "获取链接详情失败: " + e.getMessage());
        }
    }

    @GetMapping("/digital-twin/events/realtime")
    @Operation(summary = "获取实时事件", description = "获取实时供应链事件")
    public ApiResponse<List<SupplyChainEventResponse>> getRealTimeEvents() {
        try {
            List<SupplyChainEventResponse> events = digitalTwinService.getRealTimeEvents();
            return ApiResponse.success(events);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取实时事件失败: " + e.getMessage());
        }
    }

    @GetMapping("/digital-twin/events/historical")
    @Operation(summary = "获取历史数据", description = "获取指定天数的历史事件数据")
    public ApiResponse<List<SupplyChainEventResponse>> getHistoricalData(@RequestParam(defaultValue = "7") int days) {
        try {
            List<SupplyChainEventResponse> events = digitalTwinService.getHistoricalData(days);
            return ApiResponse.success(events);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取历史事件失败: " + e.getMessage());
        }
    }

    @PutMapping("/digital-twin/nodes/{id}/status")
    @Operation(summary = "更新节点状态", description = "更新指定节点的状态")
    public ApiResponse<Boolean> updateNodeStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            boolean result = digitalTwinService.updateNodeStatus(id, status);
            if (result) {
                return ApiResponse.success(true);
            } else {
                return ApiResponse.error(404, "节点不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "更新节点状态失败: " + e.getMessage());
        }
    }

    @PutMapping("/digital-twin/links/{id}/status")
    @Operation(summary = "更新链接状态", description = "更新指定链接的状态")
    public ApiResponse<Boolean> updateLinkStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            boolean result = digitalTwinService.updateLinkStatus(id, status);
            if (result) {
                return ApiResponse.success(true);
            } else {
                return ApiResponse.error(404, "链接不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "更新链接状态失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/digital-twins")
    public ResponseEntity<Map<String, Object>> getAllDigitalTwins() {
        List<DigitalTwinDTO> digitalTwins = digitalTwinService.getAllDigitalTwins();
        return ResponseEntity.ok(ResponseUtil.success(digitalTwins));
    }

    @GetMapping("/digital-twins/{id}")
    public ResponseEntity<Map<String, Object>> getDigitalTwinById(@PathVariable Long id) {
        DigitalTwinDTO digitalTwin = digitalTwinService.getDigitalTwinById(id);
        if (digitalTwin != null) {
            return ResponseEntity.ok(ResponseUtil.success(digitalTwin));
        } else {
            return ResponseEntity.ok(ResponseUtil.error(404, "数字孪生体未找到"));
        }
    }

    @PostMapping("/digital-twins")
    public ResponseEntity<Map<String, Object>> createDigitalTwin(@RequestBody DigitalTwinDTO digitalTwinDTO) {
        DigitalTwinDTO createdDigitalTwin = digitalTwinService.createDigitalTwin(digitalTwinDTO);
        return ResponseEntity.ok(ResponseUtil.success(createdDigitalTwin));
    }

    @PutMapping("/digital-twins/{id}")
    public ResponseEntity<Map<String, Object>> updateDigitalTwin(@PathVariable Long id, @RequestBody DigitalTwinDTO digitalTwinDTO) {
        DigitalTwinDTO updatedDigitalTwin = digitalTwinService.updateDigitalTwin(id, digitalTwinDTO);
        if (updatedDigitalTwin != null) {
            return ResponseEntity.ok(ResponseUtil.success(updatedDigitalTwin));
        } else {
            return ResponseEntity.ok(ResponseUtil.error(404, "数字孪生体未找到"));
        }
    }

    @DeleteMapping("/digital-twins/{id}")
    public ResponseEntity<Map<String, Object>> deleteDigitalTwin(@PathVariable Long id) {
        digitalTwinService.deleteDigitalTwin(id);
        return ResponseEntity.ok(ResponseUtil.success(null));
    }

    @GetMapping("/digital-twins/type/{type}")
    public ResponseEntity<Map<String, Object>> getDigitalTwinsByType(@PathVariable String type) {
        List<DigitalTwinDTO> digitalTwins = digitalTwinService.getDigitalTwinsByType(type);
        return ResponseEntity.ok(ResponseUtil.success(digitalTwins));
    }

    @GetMapping("/digital-twins/status/{status}")
    public ResponseEntity<Map<String, Object>> getDigitalTwinsByStatus(@PathVariable String status) {
        List<DigitalTwinDTO> digitalTwins = digitalTwinService.getDigitalTwinsByStatus(status);
        return ResponseEntity.ok(ResponseUtil.success(digitalTwins));
    }
}