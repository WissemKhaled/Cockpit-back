package com.example.demo.service;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.MessageModelMapper;
import com.example.demo.service.implementation.MessageModelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by Elimane Fofana on 2024.
 */
class MessageModelServiceTest {

    @Mock
    private MessageModelMapper messageModelMapper;

    @InjectMocks
    private MessageModelServiceImpl messageModelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMessageModelBySubContractorId() {
        // Given

        Integer subContractorId = null;
        List<MessageModel> mockResponse = Arrays.asList(new MessageModel(), new MessageModel()); // Mock response

        when(messageModelMapper.getAllMessageModelBySubcontractorId(subContractorId))
                .thenReturn(mockResponse);

        // When
        List<MessageModel> result = messageModelService.getAllMessageModelBySubcontractorId(subContractorId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockResponse, result);

        // Verify the interaction with the mock
        verify(messageModelMapper, times(1)).getAllMessageModelBySubcontractorId(subContractorId);
    }

    @Test
    void testGetAllMessageModelBySubContractorIdThrowsException() {
        // Given
        Integer subContractorId = 2;

        when(messageModelMapper.getAllMessageModelBySubcontractorId(subContractorId))
                .thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(MessageModelNotFoundException.class, () -> {
            messageModelService.getAllMessageModelBySubcontractorId(subContractorId);
        });

        // Verify the interaction with the mock
        verify(messageModelMapper, times(1)).getAllMessageModelBySubcontractorId(subContractorId);
    }

    @Test
    void testGetAllMessageModelByServiceProviderId() {
        // Given

        Integer serviceProviderId = null;
        List<MessageModel> mockResponse = Arrays.asList(new MessageModel(), new MessageModel()); // Mock response

        when(messageModelMapper.getAllMessageModelByServiceProviderId(serviceProviderId))
                .thenReturn(mockResponse);

        // When
        List<MessageModel> result = messageModelService.getAllMessageModelByServiceProviderId(serviceProviderId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockResponse, result);

        // Verify the interaction with the mock
        verify(messageModelMapper, times(1)).getAllMessageModelByServiceProviderId(serviceProviderId);
    }

    @Test
    void testGetAllMessageModelByServiceProviderIdThrowsException() {
        // Given
        Integer serviceProviderId = 2;

        when(messageModelMapper.getAllMessageModelByServiceProviderId(serviceProviderId))
                .thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(MessageModelNotFoundException.class, () -> {
            messageModelService.getAllMessageModelByServiceProviderId(serviceProviderId);
        });

        // Verify the interaction with the mock
        verify(messageModelMapper, times(1)).getAllMessageModelByServiceProviderId(serviceProviderId);
    }


}