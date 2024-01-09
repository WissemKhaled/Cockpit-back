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
    void testGetAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId() {
        // Given
        Integer subContractorStatusId = null;
        Integer serviceProviderStatusId = null;
        Integer subContractorId = null;
        Integer serviceProviderId = null;
        List<MessageModel> mockResponse = Arrays.asList(new MessageModel(), new MessageModel()); // Mock response

        when(messageModelMapper.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId))
                .thenReturn(mockResponse);

        // When
        List<MessageModel> result = messageModelService.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorStatusId, serviceProviderStatusId, subContractorId, serviceProviderId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockResponse, result);

        // Verify the interaction with the mock
        verify(messageModelMapper, times(1)).getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId);
    }

    @Test
    void testGetAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderIdThrowsException() {
        // Given
        Integer subContractorStatusId = 2;
        Integer serviceProviderStatusId = 2;
        Integer subContractorId = 2;
        Integer serviceProviderId = 2;

        when(messageModelMapper.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId))
                .thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(MessageModelNotFoundException.class, () -> {
            messageModelService.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorStatusId, serviceProviderStatusId, subContractorId, serviceProviderId);
        });

        // Verify the interaction with the mock
        verify(messageModelMapper, times(1)).getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId);
    }


}