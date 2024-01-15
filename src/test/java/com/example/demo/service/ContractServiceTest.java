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

/**
 * Created by Elimane Fofana on 2024.
 */

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
    void getContractsBySubContractorId() {
        // Given
        Integer subContractorId = 1;


        List<Contract> mockResponse = Arrays.asList(new Contract(), new Contract()); // Mock response

        when(contractMapper.getContractsBySubcontractorId(subContractorId))
                .thenReturn(mockResponse);


        // When
        List<Contract> result = contractService.getContractsBySubcontractorId(subContractorId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
      //  assertEquals(mockResponse, result);

        // Verify the interaction with the mock
        verify(contractMapper, times(1)).getContractsBySubcontractorId(subContractorId);
    }

    @Test
    void getContractsBySubContractorIdThrowsException() {
        // Given
        Integer subContractorId = 2;

        when(contractMapper.getContractsBySubcontractorId(subContractorId))
                .thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(MessageModelNotFoundException.class, () -> {
            contractService.getContractsBySubcontractorId(subContractorId);
        });

        // Verify the interaction with the mock
        verify(contractMapper, times(1)).getContractsBySubcontractorId(subContractorId);
    }

    @Test
    void getContractsByServiceProviderId() {
        // Given
        Integer serviceProviderId = 1;


        List<Contract> mockResponse = Arrays.asList(new Contract(), new Contract()); // Mock response

        when(contractMapper.getContractsByServiceProviderId(serviceProviderId))
                .thenReturn(mockResponse);


        // When
        List<Contract> result = contractService.getContractsByServiceProviderId(serviceProviderId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        //  assertEquals(mockResponse, result);

        // Verify the interaction with the mock
        verify(contractMapper, times(1)).getContractsByServiceProviderId(serviceProviderId);
    }

    @Test
    void getContractsByServiceProviderIdThrowsException() {
        // Given
        Integer serviceProviderId = 2;

        when(contractMapper.getContractsByServiceProviderId(serviceProviderId))
                .thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(MessageModelNotFoundException.class, () -> {
            contractService.getContractsByServiceProviderId(serviceProviderId);
        });

        // Verify the interaction with the mock
        verify(contractMapper, times(1)).getContractsByServiceProviderId(serviceProviderId);
    }

}