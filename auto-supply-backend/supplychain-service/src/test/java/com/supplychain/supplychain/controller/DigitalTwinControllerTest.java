package com.supplychain.supplychain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplychain.supplychain.dto.response.DigitalTwinDTO;
import com.supplychain.supplychain.service.DigitalTwinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DigitalTwinController.class)
class DigitalTwinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DigitalTwinService digitalTwinService;

    @Autowired
    private ObjectMapper objectMapper;

    private DigitalTwinDTO sampleDigitalTwin;

    @BeforeEach
    void setUp() {
        sampleDigitalTwin = new DigitalTwinDTO();
        sampleDigitalTwin.setId(1L);
        sampleDigitalTwin.setTwinName("Test Digital Twin");
        sampleDigitalTwin.setTwinType("Factory");
        sampleDigitalTwin.setStatus("Active");
    }

    @Test
    void testGetAllDigitalTwins() throws Exception {
        // Given
        List<DigitalTwinDTO> digitalTwins = Arrays.asList(sampleDigitalTwin);
        when(digitalTwinService.getAllDigitalTwins()).thenReturn(digitalTwins);

        // When & Then
        mockMvc.perform(get("/api/v1/supplychain/digital-twins")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].twinName").value("Test Digital Twin"));
    }

    @Test
    void testGetDigitalTwinById() throws Exception {
        // Given
        when(digitalTwinService.getDigitalTwinById(1L)).thenReturn(sampleDigitalTwin);

        // When & Then
        mockMvc.perform(get("/api/v1/supplychain/digital-twins/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.twinName").value("Test Digital Twin"));
    }

    @Test
    void testGetDigitalTwinById_NotFound() throws Exception {
        // Given
        when(digitalTwinService.getDigitalTwinById(1L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/v1/supplychain/digital-twins/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("数字孪生体未找到"));
    }

    @Test
    void testCreateDigitalTwin() throws Exception {
        // Given
        when(digitalTwinService.createDigitalTwin(any(DigitalTwinDTO.class))).thenReturn(sampleDigitalTwin);

        // When & Then
        mockMvc.perform(post("/api/v1/supplychain/digital-twins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleDigitalTwin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.twinName").value("Test Digital Twin"));
    }

    @Test
    void testUpdateDigitalTwin() throws Exception {
        // Given
        when(digitalTwinService.updateDigitalTwin(eq(1L), any(DigitalTwinDTO.class))).thenReturn(sampleDigitalTwin);

        // When & Then
        mockMvc.perform(put("/api/v1/supplychain/digital-twins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleDigitalTwin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.twinName").value("Test Digital Twin"));
    }

    @Test
    void testUpdateDigitalTwin_NotFound() throws Exception {
        // Given
        when(digitalTwinService.updateDigitalTwin(eq(1L), any(DigitalTwinDTO.class))).thenReturn(null);

        // When & Then
        mockMvc.perform(put("/api/v1/supplychain/digital-twins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleDigitalTwin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("数字孪生体未找到"));
    }

    @Test
    void testDeleteDigitalTwin() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/supplychain/digital-twins/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testGetDigitalTwinsByType() throws Exception {
        // Given
        List<DigitalTwinDTO> digitalTwins = Arrays.asList(sampleDigitalTwin);
        when(digitalTwinService.getDigitalTwinsByType("Factory")).thenReturn(digitalTwins);

        // When & Then
        mockMvc.perform(get("/api/v1/supplychain/digital-twins/type/Factory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].twinName").value("Test Digital Twin"));
    }

    @Test
    void testGetDigitalTwinsByStatus() throws Exception {
        // Given
        List<DigitalTwinDTO> digitalTwins = Arrays.asList(sampleDigitalTwin);
        when(digitalTwinService.getDigitalTwinsByStatus("Active")).thenReturn(digitalTwins);

        // When & Then
        mockMvc.perform(get("/api/v1/supplychain/digital-twins/status/Active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].twinName").value("Test Digital Twin"));
    }
}