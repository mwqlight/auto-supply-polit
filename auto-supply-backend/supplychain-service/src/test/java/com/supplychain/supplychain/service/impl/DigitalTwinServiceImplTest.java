package com.supplychain.supplychain.service.impl;

import com.supplychain.supplychain.entity.DigitalTwin;
import com.supplychain.supplychain.repository.DigitalTwinRepository;
import com.supplychain.supplychain.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DigitalTwinServiceImplTest {

    @Mock
    private DigitalTwinRepository digitalTwinRepository;

    @InjectMocks
    private DigitalTwinServiceImpl digitalTwinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDigitalTwins() {
        // Given
        DigitalTwin twin1 = new DigitalTwin();
        twin1.setId(1L);
        twin1.setTwinName("Twin 1");
        twin1.setTwinType("Type A");
        twin1.setStatus("active");
        
        DigitalTwin twin2 = new DigitalTwin();
        twin2.setId(2L);
        twin2.setTwinName("Twin 2");
        twin2.setTwinType("Type B");
        twin2.setStatus("inactive");
        
        when(digitalTwinRepository.findAll()).thenReturn(Arrays.asList(twin1, twin2));

        // When
        List<DigitalTwinDTO> result = digitalTwinService.getAllDigitalTwins();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Twin 1", result.get(0).getTwinName());
        assertEquals("Twin 2", result.get(1).getTwinName());
        
        verify(digitalTwinRepository, times(1)).findAll();
    }

    @Test
    void testGetDigitalTwinById() {
        // Given
        DigitalTwin twin = new DigitalTwin();
        twin.setId(1L);
        twin.setTwinName("Test Twin");
        twin.setTwinType("Test Type");
        twin.setStatus("active");
        twin.setCreatedAt(LocalDateTime.now());
        
        when(digitalTwinRepository.findById(1L)).thenReturn(Optional.of(twin));

        // When
        DigitalTwinDTO result = digitalTwinService.getDigitalTwinById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Twin", result.getTwinName());
        assertEquals("Test Type", result.getTwinType());
        
        verify(digitalTwinRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDigitalTwinById_NotFound() {
        // Given
        when(digitalTwinRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        DigitalTwinDTO result = digitalTwinService.getDigitalTwinById(1L);

        // Then
        assertNull(result);
        
        verify(digitalTwinRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateDigitalTwin() {
        // Given
        DigitalTwinDTO inputDto = new DigitalTwinDTO();
        inputDto.setTwinName("New Twin");
        inputDto.setTwinType("New Type");
        inputDto.setStatus("active");

        DigitalTwin savedTwin = new DigitalTwin();
        savedTwin.setId(1L);
        savedTwin.setTwinName("New Twin");
        savedTwin.setTwinType("New Type");
        savedTwin.setStatus("active");
        savedTwin.setCreatedAt(LocalDateTime.now());

        when(digitalTwinRepository.save(any(DigitalTwin.class))).thenReturn(savedTwin);

        // When
        DigitalTwinDTO result = digitalTwinService.createDigitalTwin(inputDto);

        // Then
        assertNotNull(result);
        assertEquals("New Twin", result.getTwinName());
        assertEquals("New Type", result.getTwinType());
        assertNotNull(result.getId());
        
        verify(digitalTwinRepository, times(1)).save(any(DigitalTwin.class));
    }

    @Test
    void testUpdateDigitalTwin() {
        // Given
        DigitalTwin existingTwin = new DigitalTwin();
        existingTwin.setId(1L);
        existingTwin.setTwinName("Old Name");
        existingTwin.setTwinType("Old Type");
        existingTwin.setStatus("active");
        existingTwin.setUpdatedAt(LocalDateTime.now());

        DigitalTwinDTO updateDto = new DigitalTwinDTO();
        updateDto.setTwinName("Updated Name");
        updateDto.setTwinType("Updated Type");
        updateDto.setStatus("inactive");

        when(digitalTwinRepository.findById(1L)).thenReturn(Optional.of(existingTwin));
        when(digitalTwinRepository.save(any(DigitalTwin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        DigitalTwinDTO result = digitalTwinService.updateDigitalTwin(1L, updateDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Name", result.getTwinName());
        assertEquals("Updated Type", result.getTwinType());
        assertEquals("inactive", result.getStatus());
        
        verify(digitalTwinRepository, times(1)).findById(1L);
        verify(digitalTwinRepository, times(1)).save(any(DigitalTwin.class));
    }

    @Test
    void testUpdateDigitalTwin_NotFound() {
        // Given
        DigitalTwinDTO updateDto = new DigitalTwinDTO();
        updateDto.setTwinName("Updated Name");

        when(digitalTwinRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        DigitalTwinDTO result = digitalTwinService.updateDigitalTwin(1L, updateDto);

        // Then
        assertNull(result);
        
        verify(digitalTwinRepository, times(1)).findById(1L);
        verify(digitalTwinRepository, never()).save(any(DigitalTwin.class));
    }

    @Test
    void testDeleteDigitalTwin() {
        // Given
        doNothing().when(digitalTwinRepository).deleteById(1L);

        // When
        digitalTwinService.deleteDigitalTwin(1L);

        // Then
        verify(digitalTwinRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetDigitalTwinsByType() {
        // Given
        DigitalTwin twin1 = new DigitalTwin();
        twin1.setId(1L);
        twin1.setTwinName("Twin 1");
        twin1.setTwinType("Type A");
        twin1.setStatus("active");
        
        DigitalTwin twin2 = new DigitalTwin();
        twin2.setId(2L);
        twin2.setTwinName("Twin 2");
        twin2.setTwinType("Type A");
        twin2.setStatus("inactive");
        
        when(digitalTwinRepository.findByTwinType("Type A")).thenReturn(Arrays.asList(twin1, twin2));

        // When
        List<DigitalTwinDTO> result = digitalTwinService.getDigitalTwinsByType("Type A");

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Type A", result.get(0).getTwinType());
        assertEquals("Type A", result.get(1).getTwinType());
        
        verify(digitalTwinRepository, times(1)).findByTwinType("Type A");
    }

    @Test
    void testGetDigitalTwinsByStatus() {
        // Given
        DigitalTwin twin1 = new DigitalTwin();
        twin1.setId(1L);
        twin1.setTwinName("Twin 1");
        twin1.setTwinType("Type A");
        twin1.setStatus("active");
        
        DigitalTwin twin2 = new DigitalTwin();
        twin2.setId(2L);
        twin2.setTwinName("Twin 2");
        twin2.setTwinType("Type B");
        twin2.setStatus("active");
        
        when(digitalTwinRepository.findByStatus("active")).thenReturn(Arrays.asList(twin1, twin2));

        // When
        List<DigitalTwinDTO> result = digitalTwinService.getDigitalTwinsByStatus("active");

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("active", result.get(0).getStatus());
        assertEquals("active", result.get(1).getStatus());
        
        verify(digitalTwinRepository, times(1)).findByStatus("active");
    }
}