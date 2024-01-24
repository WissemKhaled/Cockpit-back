package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Created by Elimane Fofana on 2024.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // pour utiliser @BeforeAll et @AfterAll dans les méthodes non
												// statiques,on doit ajouter à notre test
												// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
												// pour éviter la lancement de JUnitException
class ContractControllerTest {
	
	
	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${token.expiration.duration}")
	private int tokenExpirationDuration;

	private String baseUrl = "/contract/";
	private String jwtToken;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	private void setUpBeforeClass() {
		jwtToken = createToken("john@test.fr");
	}
	
	
	@Test
	public void testGetContractsByServiceProviderId_ShouldReturnHttpStatusOk() throws Exception {
		String serviceProviderId = "1";

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "getContractsByServiceProviderId").param("serviceProviderId",serviceProviderId).header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.spFirstName").value("Firstspfirstname")) 
//		.andExpect(jsonPath("$.spName").value("FIRSTSPNAME"))
//		.andExpect(jsonPath("$.spEmail").value("Sp1@email.com"))
//		.andExpect(jsonPath("$.subcontractorSName").value("Orange"))
//		.andExpect(jsonPath("$.spStatus.stId").value(3))
		;
	}
	
	private String createToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		// Add any additional claims if needed
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * tokenExpirationDuration))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}


//    @Autowired
//    private MockMvc mockMvc; // Inject MockMvc for simulating HTTP requests
//
//    @MockBean
//    private ContractService contractService; // Create a mock implementation of contractService
//
//    private String jwtToken; // Variable to store a JWT token for testing
//
//    @BeforeEach
//    void setUp() {
//        // Initialize a placeholder JWT token before each test
//        jwtToken = "Bearer  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQHRlc3QuZnIiLCJpYXQiOjE3MDQ3ODk1NDQsImV4cCI6MTcwNTE5OTYxMH0.Ylrez3dFJw2l_npcquivixS5fSEmPh0AUjAMLzE6MKk";
//    }
//
//
//    @Test
//    void getAllMessageModelsBySubcontractorId() throws Exception {
//        // Define test parameters
//        Integer subContractorId = 1;
//
//        // Create a list of mock Contract objects for the test
//        List<Contract> mockContracts = Collections.singletonList(new Contract());
//
//        // Define behavior of mocked service method
//        when(contractService.getContractsBySubcontractorId(subContractorId))
//                .thenReturn(mockContracts);
//
//        // Perform a GET request to the specified URL and set up the request
//        MvcResult result = mockMvc.perform(get("/contract/getContractsBySubcontractorId")
//                        .header("Authorization", jwtToken) // Add JWT token to the request header for authorization
//                        .param("subContractorId", String.valueOf(subContractorId)) // Add 'subContractorId' as a request parameter
//                        .contentType(MediaType.APPLICATION_JSON)) // Set the content type of the request to JSON
//                .andExpect(status().isOk()) // Assert that the response status is 200 OK
//                .andExpect(jsonPath("$.content").exists()) // Assert that there is a 'content' field in the JSON response
//                .andReturn(); // Return the result of the MVC request for further analysis or assertions
//
//        // Display the response body
//        System.out.println("Response Body: " + result.getResponse().getContentAsString());
//
//        // If you also want to display the status code
//        System.out.println("Response Status: " + result.getResponse().getStatus());
//
//        // Verify the service method was called with the correct parameters
//        verify(contractService).getContractsBySubcontractorId(subContractorId);
//    }
//
//    @Test
//    void getAllMessageModelsByServiceProviderId() throws Exception {
//        // Define test parameters
//        Integer serviceProviderId = 1;
//
//        // Create a list of mock Contract objects for the test
//        List<Contract> mockContracts = Collections.singletonList(new Contract());
//
//        // Define behavior of mocked service method
//        when(contractService.getContractsByServiceProviderId(serviceProviderId))
//                .thenReturn(mockContracts);
//
//        // Perform a GET request to the specified URL and set up the request
//        MvcResult result = mockMvc.perform(get("/contract/getContractsByServiceProviderId") // Corrected URL
//                        .header("Authorization", jwtToken) // Add JWT token to the request header for authorization
//                        .param("serviceProviderId", String.valueOf(serviceProviderId)) // Add 'serviceProviderId' as a request parameter
//                        .contentType(MediaType.APPLICATION_JSON)) // Set the content type of the request to JSON
//                .andExpect(status().isOk()) // Assert that the response status is 200 OK
//                .andExpect(jsonPath("$.content").exists()) // Assert that there is a 'content' field in the JSON response
//                .andReturn(); // Return the result of the MVC request for further analysis or assertions
//
//        // Display the response body
//        System.out.println("Response Body: " + result.getResponse().getContentAsString());
//
//        // If you also want to display the status code
//        System.out.println("Response Status: " + result.getResponse().getStatus());
//
//        // Verify the service method was called with the correct parameters
//        verify(contractService).getContractsByServiceProviderId(serviceProviderId); // Corrected method verification
//    }
}

