package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.exception.ServiceProviderNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ServiceProviderControllerTest {

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${token.expiration.duration}")
	private int tokenExpirationDuration;

	private String baseUrl = "/service-providers/";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetServiceProvider_ShouldReturnHttpStatusOk() throws Exception {
		int expectedSpId = 1;
		String jwtToken = createToken("john@test.fr");

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + expectedSpId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.spFirstName").value("sp-1-first_name"))
		.andExpect(jsonPath("$.spName").value("sp-1-name"))
		.andExpect(jsonPath("$.spEmail").value("sp1@email.com"))
		.andExpect(jsonPath("$.subcontractorId").value(1))
		.andExpect(jsonPath("$.spStatusId").value(3));
	}

	@Test
	public void testGetServiceProvider_ShouldReturnHttpStatusNotFound() throws Exception {
		int nonExistingServiceProviderId = Integer.MAX_VALUE - 16;
		String jwtToken = createToken("john@test.fr");

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + nonExistingServiceProviderId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testInsertServiceProvider_ShouldReturnHttpStatusCreated() throws Exception {
		int expectedSpId = Integer.MAX_VALUE - 5456;
		String jwtToken = createToken("john@test.fr");

		ServiceProviderDto nonExistingServiceProviderTosave = new ServiceProviderDto();
		nonExistingServiceProviderTosave.setSpId(5);
		nonExistingServiceProviderTosave.setSpName("sp-5-name");
		nonExistingServiceProviderTosave.setSpFirstName("sp-5-first_name");
		nonExistingServiceProviderTosave.setSpEmail("sp5@email.com");
		nonExistingServiceProviderTosave.setSubcontractorId(1);
		nonExistingServiceProviderTosave.setSpStatutId(1);

		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "save")
				.content(asJsonString(nonExistingServiceProviderTosave))
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.spFirstName").value("sp-5-first_name"))
		.andExpect(jsonPath("$.spName").value("sp-5-name"))
		.andExpect(jsonPath("$.spEmail").value("sp5@email.com"))
		.andExpect(jsonPath("$.subcontractorId").value(1))
		.andExpect(jsonPath("$.spStatusId").value(1));
	}

	@Test
	public void testUpdateServiceProvider_UpdateExistingServiceProvider_ShouldReturnHttpOk() throws Exception {
		String jwtToken = createToken("john@test.fr");

		ServiceProviderDto existingServiceProviderTosave = new ServiceProviderDto();
		existingServiceProviderTosave.setSpId(1);
		existingServiceProviderTosave.setSpName("sp-1-name");
		existingServiceProviderTosave.setSpFirstName("sp-1-first_name");
		existingServiceProviderTosave.setSpEmail("sp1@email.com");
		existingServiceProviderTosave.setSubcontractorId(1);
		existingServiceProviderTosave.setSpStatutId(3);

		ServiceProviderDto ServiceProviderForUpdate = new ServiceProviderDto();
		ServiceProviderForUpdate.setSpId(1);
		ServiceProviderForUpdate.setSpName("sp-1-name_UPDATED");
		ServiceProviderForUpdate.setSpFirstName("sp-1-first_name_UPDATED");
		ServiceProviderForUpdate.setSpEmail("sp1@email-UPDATED.com");
		ServiceProviderForUpdate.setSubcontractorId(2);
		ServiceProviderForUpdate.setSpStatutId(1);

		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "save")
				.content(asJsonString(ServiceProviderForUpdate))
				.header("Authorization", "Bearer " + jwtToken).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.spFirstName").value("sp-1-first_name_UPDATED"))
		.andExpect(jsonPath("$.spName").value("sp-1-name_UPDATED"))
		.andExpect(jsonPath("$.spEmail").value("sp1@email-UPDATED.com"))
		.andExpect(jsonPath("$.subcontractorId").value(2))
		.andExpect(jsonPath("$.spStatusId").value(1));
	}

	@Test
	public void archiveTest_ArchiveExistingServiceProvider_ShouldReturnOk() throws Exception{
		String jwtToken = createToken("john@test.fr");
		int existingNonArchivedServiceProviderId = 1;
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "archive/" + existingNonArchivedServiceProviderId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void archiveTest_ArchiveExistingServiceProviderFailed_ShouldReturnAlreadyArchivedException()
			throws Exception {
		String jwtToken = createToken("john@test.fr");
		int existingArchivedServiceProviderId = 4;

		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "archive/" + existingArchivedServiceProviderId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("le prestataire avec l'id "+ existingArchivedServiceProviderId +" est déjà archivé."));
	}
	
	@Test
	public void getTest_GetServiceProvidersBySubcontractorId_ShouldReturnListOfTwoServiceProviders() throws Exception {
		String jwtToken = createToken("john@test.fr");
		int existingSubcontractorId = 1;
		
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"all-service-providers/"+existingSubcontractorId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].spName").value("sp-1-name"))
        .andExpect(jsonPath("$[0].spFirstName").value("sp-1-first_name"))
        .andExpect(jsonPath("$[1].spName").value("sp-2-name"))
        .andExpect(jsonPath("$[1].spFirstName").value("sp-2-first_name"));

	}
	
	@Test
	public void getTest_GetServiceProvidersBySubcontractorIdFailed_ShouldReturnServiceProvidersNotFoundException() throws Exception {
		String jwtToken = createToken("john@test.fr");
		int SubcontractorIdHasNoServiceProviders = 5;
		
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"all-service-providers/"+SubcontractorIdHasNoServiceProviders)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
        .andExpect(content().string(String.format("le sous-traitant avec l'id: %d n'a pas de prestataires", SubcontractorIdHasNoServiceProviders)));
	}

	// method pour convertir un objet Java en sa représentation JSON sous forme de
	// chaîne de caractères.
	public static String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
}
