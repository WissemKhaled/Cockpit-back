package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controller.exception.SubcontractorNotFoundException;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.mappers.SubcontractorMapper;

@ExtendWith(MockitoExtension.class)
public class SubcontractorServiceTest {

    @Mock
    private SubcontractorMapper subcontractorMapper;

    @InjectMocks
    private SubcontractorServiceImpl subcontractorService;

    @BeforeEach
    public void setup() {
        // You might want to initialize any specific behavior before each test here
    }

    @Test
    public void testGetSubcontractorWithStatus_ExistingId() {
        // Mocking behavior of SubcontractorMapper to return a dummy Subcontractor
        int existingId = 1;
        Subcontractor dummySubcontractor = new Subcontractor();
        dummySubcontractor.setSId(existingId);
        dummySubcontractor.setSName("Orange");
        dummySubcontractor.setSEmail("Orange@email.fr");
        dummySubcontractor.setStatus(new Status(1,"EN_COURS","AAAA"));
        // Set properties of the dummySubcontractor

        // Mocking behavior to return the dummySubcontractor when the mapper is called
        when(subcontractorMapper.getSubcontractorWithStatus(anyInt())).thenReturn(dummySubcontractor);

        // Perform the service method call
        Subcontractor result = subcontractorService.getSubcontractorWithStatus(1);

        // Assertions to check if the result matches the expected dummy Subcontractor
        assertEquals(dummySubcontractor, result);
        // You can perform more specific property-level assertions if needed
    }
    
    @Test
    public void testGetSubcontractorWithStatus_NonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;
        // Mocking behavior of SubcontractorMapper to return null for a non-existing ID
        when(subcontractorMapper.getSubcontractorWithStatus(anyInt())).thenReturn(null);

        // Perform the service method call, expecting a SubcontractorNotFoundException
        try {
            subcontractorService.getSubcontractorWithStatus(nonExistingId);
        } catch (SubcontractorNotFoundException e) {
            assertEquals("le sous-traitant avec l'id: " + nonExistingId + " n'existe pas!!", e.getMessage());
        }
    }
    
}