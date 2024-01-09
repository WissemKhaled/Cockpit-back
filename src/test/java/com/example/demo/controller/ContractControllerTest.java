package com.example.demo.controller;

import com.example.demo.entity.Contract;
import com.example.demo.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Elimane Fofana on 2024.
 */
@WebMvcTest(controllers= ContractController.class) // Use for MVC testing, focusing on MessageModelController
@AutoConfigureMockMvc(addFilters = false) // Auto-configure MockMvc without adding any standard servlet filters
@ExtendWith(MockitoExtension.class) // Integrate Mockito with JUnit 5 for mocking
class ContractControllerTest {


    @Autowired
    private MockMvc mockMvc; // Inject MockMvc for simulating HTTP requests

    @MockBean
    private ContractService contractService; // Create a mock implementation of contractService

    private String jwtToken; // Variable to store a JWT token for testing

    @BeforeEach
    void setUp() {
        // Initialize a placeholder JWT token before each test
        jwtToken = "Bearer  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQHRlc3QuZnIiLCJpYXQiOjE3MDQ3ODk1NDQsImV4cCI6MTcwNTE5OTYxMH0.Ylrez3dFJw2l_npcquivixS5fSEmPh0AUjAMLzE6MKk";
    }

    @Test
    void getAllMessageModelsAndStatusForSubcontractorCategory() throws Exception {
        // Define test parameters
        Integer subContractorId = 1;
        Integer serviceProviderId = null;
        Integer messageModelId = null;
        Pageable pageable = PageRequest.of(0, 6);

        // Create a list of mock Contract objects for the test
        List<Contract> mockContracts = Collections.singletonList(new Contract());

        // Define behavior of mocked service method
        when(contractService.getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId(serviceProviderId, subContractorId, messageModelId))
                .thenReturn(mockContracts);

        // Perform a GET request to the specified URL and set up the request
       MvcResult result = mockMvc.perform(get("/contract/getContractsById")
                        .header("Authorization", jwtToken) // Add JWT token to the request header for authorization
                        .param("subContractorId", subContractorId != null ? String.valueOf(subContractorId) : null) // Add 'subContractorId' as a request parameter if it's not null
                        .param("serviceProviderId", serviceProviderId != null ? String.valueOf(serviceProviderId) : null) // Add 'serviceProviderId' as a request parameter if it's not null
                        .param("messageModelId", messageModelId != null ? String.valueOf(messageModelId) : null) // Add 'messageModelId' as a request parameter if it's not null
                        .contentType(MediaType.APPLICATION_JSON)) // Set the content type of the request to JSON
                .andExpect(status().isOk()) // Assert that the response status is 200 OK
                .andExpect(jsonPath("$.content").exists()) // Assert that there is a 'content' field in the JSON response
                .andReturn(); // Return the result of the MVC request for further analysis or assertions


        // Display the response body
        System.out.println("Response Body: " + result.getResponse().getContentAsString());

        // If you also want to display the status code
        System.out.println("Response Status: " + result.getResponse().getStatus());

        // Verify the service method was called with the correct parameters
        verify(contractService).getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId(serviceProviderId, subContractorId, messageModelId);
    }
    }

