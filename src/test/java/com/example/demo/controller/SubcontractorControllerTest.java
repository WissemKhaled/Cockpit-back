package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.http.HttpResponse;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.service.SubcontractorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // pour utiliser @BeforeAll et @AfterAll dans les méthodes non
												// statiques,on doit ajouter à notre test
												// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
												// pour éviter la lancement de JUnitException
public class SubcontractorControllerTest {
	
	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${token.expiration.duration}")
	private int tokenExpirationDuration;

	private String jwtToken;

	private String baseUrl = "/subcontractor/";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private SubcontractorService subcontractorService;
	
	@Mock
	private SubcontractorService service;
	
	@BeforeAll
	private void setUpBeforeClass() {
		jwtToken = createToken("john@test.fr");
	}


//	@Test
//	public void getAllSubcontractorAPI() throws Exception {
//
//		mvc.perform(
//				MockMvcRequestBuilders.get("/subcontractor/getAll?nameColonne=s_id&sorting=desc&pageSize=1&page=10")
//						.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//	}
//
//	@Test
//	public void getAllSubcontractorAPI_isEmpty() throws Exception {
//		
//        when(service.getAllSubcontractor(any(), any(), anyInt(), anyInt()))
//        .thenThrow(new RuntimeException("An exception occurred"));
//		
//	       mockMvc.perform(get("/subcontractor/getAll")
//	                .param("nameColonne", "s_id")
//	                .param("sorting", "asc")
//	                .param("pageSize", "10")
//	                .param("page", "1"))
//	                .andExpect(status().isBadRequest());
//	}

	@Test
	void testGetSubcontractor_SubcontractorOk() throws Exception {
		int expectedSId = 1;

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + expectedSId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sId").value(expectedSId))
				.andExpect(jsonPath("$.sName").value("Orange"))
				.andExpect(jsonPath("$.sEmail").value("Orange@email.fr"))
				.andExpect(jsonPath("$.sLastUpdateDate").isEmpty())
				.andExpect(jsonPath("$.status.stId").value(1))
				.andExpect(jsonPath("$.status.stName").value("En cours"));
		}
	
    private LocalDateTime dateFormatter(String dateString) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    	LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
    	return dateTime;
	}

	@Test
	void testGetSubcontractor_SubcontractorNotFound_ShouldReturnHttpNotFound() throws Exception {
		int subcontractorId = Integer.MAX_VALUE;

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + subcontractorId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testSaveSubcontractor_saveNewSubcontractor_ShouldReturnHttpCreated() throws Exception {
		Subcontractor nonExistingSubcontractorTosave = new Subcontractor() ;  
		nonExistingSubcontractorTosave.setSId(99991);
		nonExistingSubcontractorTosave.setSName("test_saving");
		nonExistingSubcontractorTosave.setSEmail("test_saving@email.fr");
		nonExistingSubcontractorTosave.setStatus(new Status(1));

		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
				.content(asJsonString(nonExistingSubcontractorTosave))
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_saving"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("test_saving@email.fr"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(1))
				.andExpect(status().isCreated());
	}

//	@Test
//	public void testSaveSubcontractor_UpdateExistingSubcontractor_ShouldReturnHttpOk() throws Exception {
//		Subcontractor savedSubcontractor = new Subcontractor();
//		savedSubcontractor.setSId(Integer.MAX_VALUE);
//		savedSubcontractor.setSName("testUpdating_0");
//		savedSubcontractor.setSEmail("testUpdating_0@email.fr");
//		savedSubcontractor.setStatus(new Status(1));
//		int savedSubcontractorId = subcontractorService.saveSubcontractor(savedSubcontractor);
//
//		SubcontractorDto updatedSubcontractorDto = new SubcontractorDto();
//		updatedSubcontractorDto.setSId(savedSubcontractorId);
//		updatedSubcontractorDto.setSName("testUpdating_1");
//		updatedSubcontractorDto.setSEmail("testUpdating_1@email.fr");
//		updatedSubcontractorDto.setStatus(new Status(3));
//
//		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save").content(asJsonString(updatedSubcontractorDto))
//				.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("testUpdating_1"))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("testUpdating_1@email.fr"))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(3));
//	}

	@Test
	void testArchiveSubcontractor_SuccessfulArchive_ShouldReturnHttpOk() throws Exception {
		int subcontractorToArchiveId = 1;

		mockMvc.perform(MockMvcRequestBuilders
				.put(baseUrl + "/archive/" + subcontractorToArchiveId)
				.header("Authorization", "Bearer " + jwtToken)
				.content(asJsonString(subcontractorToArchiveId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testArchiveSubcontractor_FailedArchive_ShouldReturnAlreadyArchivedSubcontractorException()
			throws Exception {
		int alreadyArchivedSubcontractorId = 103;

		mockMvc.perform(
				MockMvcRequestBuilders.put(baseUrl + "/archive/" + alreadyArchivedSubcontractorId)
						.content(asJsonString(alreadyArchivedSubcontractorId))
						.header("Authorization", "Bearer " + jwtToken)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testArchiveSubcontractor_FailedArchive_InvalidId_ShouldReturnHttpNotAcceptable() throws Exception {
		String invalidId = "e";
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/archive/" + invalidId)
				.header("Authorization", "Bearer " + jwtToken)
				.content(asJsonString(invalidId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable());
	}

	@Test
	void testArchiveSubcontractor_FailedArchive_NonExistingSubcontractor_ShouldReturnHttpNotFound()
			throws Exception {
		int nonExistingSubcontarctorId = Integer.MAX_VALUE;
		mockMvc.perform(
				MockMvcRequestBuilders.put(baseUrl + "/archive/" + nonExistingSubcontarctorId)
						.content(asJsonString(nonExistingSubcontarctorId))
						.header("Authorization", "Bearer " + jwtToken)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/**
	 * Convertir un objet Java en sa représentation sous forme de chaîne JSON.
	 * Cette méthode utilise l'ObjectMapper de Jackson pour effectuer la sérialisation.
	 *
	 * @param object L'objet Java à sérialiser en JSON.
	 * @return Une chaîne au format JSON représentant l'objet d'entrée.
	 * @throws RuntimeException Si une exception survient pendant le processus de sérialisation.
	 */
	public static String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Crée un jeton JWT (JSON Web Token) pour un utilisateur donné.
	 *
	 * @param userName Le nom d'utilisateur pour lequel générer le jeton.
	 * @return Le jeton JWT généré.
	 */
	private String createToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		// Add any additional claims if needed
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * tokenExpirationDuration))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Obtient la clé de signature HMAC à partir de la chaîne secrète encodée en Base64.
	 *
	 * @return La clé de signature HMAC.
	 */
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
