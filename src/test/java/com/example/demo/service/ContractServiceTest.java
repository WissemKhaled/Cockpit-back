package com.example.demo.service;

import com.example.demo.entity.Contract;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.ContractMapper;
import com.example.demo.service.implementation.ContractServiceImpl;
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

class ContractServiceTest {

    @Mock
    private ContractMapper contractMapper;

    @InjectMocks
    private ContractServiceImpl contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId() {
        // Given
        Integer subContractorId = 1;
        Integer serviceProviderId = null;
        Integer modelMessageId = null;

        List<Contract> mockResponse = Arrays.asList(new Contract(), new Contract()); // Mock response

        when(contractMapper.getContractsByMessageModelId(serviceProviderId, subContractorId, modelMessageId))
                .thenReturn(mockResponse);

        // When
        List<Contract> result = contractService.getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId(serviceProviderId, subContractorId, modelMessageId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockResponse, result);

        // Verify the interaction with the mock
        verify(contractMapper, times(1)).getContractsByMessageModelId(serviceProviderId, subContractorId, modelMessageId);
    }

    @Test
    void getContractsBySubContractorIdOrServiceProviderIdOrMessageModelIdThrowsException() {
        // Given
        Integer messageModelId = 2;
        Integer subContractorId = 2;
        Integer serviceProviderId = 2;

        when(contractMapper.getContractsByMessageModelId(serviceProviderId, subContractorId, messageModelId))
                .thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(MessageModelNotFoundException.class, () -> {
            contractService.getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId(serviceProviderId, subContractorId, messageModelId);
        });

        // Verify the interaction with the mock
        verify(contractMapper, times(1)).getContractsByMessageModelId(serviceProviderId, subContractorId, messageModelId);
    }

}