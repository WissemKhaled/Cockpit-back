package com.example.demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.builder.SubcontractorBuilder;
import com.example.demo.builder.dto.SubcontractorDtoBuilder;
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

	private String baseUrl = "/subcontractor";
	
	@Autowired
	private MockMvc mockMvc;
	
//	@MockBean
	@Autowired
	private SubcontractorService subcontractorService;
	
//	@Mock
//	private SubcontractorService sService;
	
//    @InjectMocks
//    private SubcontractorController subcontractorController;
	
	@BeforeAll
	private void setUpBeforeClass() {
		jwtToken = createToken("john@test.fr");
	}

	@Test
	void testGetAllNonSubcontractor_whenRetreivingANonEmptySubcontractorsList_ShouldReturnOkHttpStatus() throws Exception {
		// GIVEN
		String sortingMethod = "asc";
		String pageNumber = "1";
		String pageSize = "10";
		
		// WHEN / THEN
		mockMvc.perform(get(baseUrl + "/all-subcontractors")
				.param("sortingMethod", sortingMethod)
				.param("pageNumber", pageNumber)
				.param("pageSize", pageSize)
				.header("Authorization", "Bearer " + jwtToken))	
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(10)))
		;
	}
	
//	@Test
//	void testGetAllNonSubcontractor_whenRuntimeExceptionIsThrown_ShouldReturnBadRequestHttpStatus() throws Exception {
//		// GIVEN
//		String sortingMethod = "asc";
//		String pageNumber = "1";
//		String pageSize = "10";
//		
//		// WHEN 
//        when(subcontractorService.getAllNonArchivedSubcontractors(any(), anyInt(), anyInt()))
//        .thenThrow(new RuntimeException("An exception occurred"));
//        
//		// THEN
//	       mockMvc.perform(get(baseUrl + "/all-subcontractors")
//					.param("sortingMethod", sortingMethod)
//					.param("pageNumber", pageNumber)
//					.param("pageSize", pageSize)
//			.header("Authorization", "Bearer " + jwtToken))	
//	                .andExpect(status().isBadRequest());
//	}
//	
//	@Test
//	void testGetAllNonSubcontractor_whenEntityNotFoundExceptionIsThrown_ShouldReturnNotFoundHttpStatus() throws Exception {
//		// GIVEN
//		String sortingMethod = "asc";
//		String pageNumber = "1";
//		String pageSize = "10";
//		
//		// WHEN 
//		when(subcontractorService.getAllNonArchivedSubcontractors(any(), anyInt(), anyInt()))
//		.thenThrow(new EntityNotFoundException("Il n'y a pas de sous-traitans enregistré"));
//		
//		// THEN
//		mockMvc.perform(get(baseUrl + "/all-subcontractors")
//				.param("sortingMethod", sortingMethod)
//				.param("pageNumber", pageNumber)
//				.param("pageSize", pageSize)
//				.header("Authorization", "Bearer " + jwtToken))	
//		.andExpect(status().isNotFound());
//	}
	
	@Test
	void testGetNumberOfAllNonArchivedSubcontractors_whenRetreivingANonEmptySubcontractorsList_ShouldReturnOkHttpStatus() throws Exception {
		// GIVEN

		// WHEN / THEN
		mockMvc.perform(get(baseUrl + "/count-all-subcontractors")
				.header("Authorization", "Bearer " + jwtToken))	
		.andExpect(status().isOk());
	}
	
//	@Test
//	void testGetNumberOfAllNonArchivedSubcontractors_whenRuntimeExceptionIsThrown_ShouldReturnBadRequestHttpStatus() throws Exception {
//		// GIVEN
//
//		// WHEN
//		when(subcontractorService.getNumberOfAllNonSubcontractors())
//        .thenThrow(new RuntimeException("An exception occurred"));
//		
//		// THEN
//		mockMvc.perform(get(baseUrl + "/count-all-subcontractors")
//				.header("Authorization", "Bearer " + jwtToken))	
//		.andExpect(status().isBadRequest());
//	}
//	
//	@Test
//	void testGetNumberOfAllNonArchivedSubcontractors_whenEntityNotFoundExceptionIsThrown_ShouldReturnNotFoundHttpStatus() throws Exception {
//		// GIVEN
//
//		// WHEN
//		when(subcontractorService.getNumberOfAllNonSubcontractors())
//		.thenThrow(new EntityNotFoundException("Il n'y a pas de sous-traitans enregistré"));
//		
//		// THEN
//		mockMvc.perform(get(baseUrl + "/count-all-subcontractors")
//				.header("Authorization", "Bearer " + jwtToken))	
//		.andExpect(status().isNotFound());
//	}
	
	@Test
	void testGetAllSubcontractorWithStatus_whenRetreivingANonEmptySubcontractorsList_ShouldReturnOkHttpStatus() throws Exception {
		// GIVEN
		String sortingMethod = "asc";
		String pageNumber = "1";
		String pageSize = "10";
		String statusId = "1";
		
		// WHEN / THEN
		mockMvc.perform(get(baseUrl + "/all-subcontractors/status")
				.param("sortingMethod", sortingMethod)
				.param("pageNumber", pageNumber)
				.param("pageSize", pageSize)
				.param("statusId", statusId)
				.header("Authorization", "Bearer " + jwtToken))	
		.andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(10)))
		.andExpect(jsonPath("$[0].sName").value("Alstom"))
		.andExpect(jsonPath("$[1].sName").value("ByteBurst Technologies"))
		;
	}
//	
//	@Test
//	void testGetAllSubcontractorWithStatus_whenRuntimeExceptionIsThrown_ShouldReturnBadRequestHttpStatus() throws Exception {
//		// GIVEN
//		String sortingMethod = "asc";
//		String pageNumber = "1";
//		String pageSize = "10";
//		String statusId = "1";
//
//		// WHEN 
//		when(subcontractorService.getAllSubcontractorWithStatus(any(), anyInt(), anyInt(), anyInt()))
//        .thenThrow(new RuntimeException("An exception occurred"));
//        
//		// THEN
//	       mockMvc.perform(get(baseUrl + "/all-subcontractors/status")
//					.param("sortingMethod", sortingMethod)
//					.param("pageNumber", pageNumber)
//					.param("pageSize", pageSize)
//					.param("statusId", statusId)
//			.header("Authorization", "Bearer " + jwtToken))	
//	                .andExpect(status().isBadRequest());
//	}
//	
//	@Test
//	void testGetAllSubcontractorWithStatus_whenEntityNotFoundExceptionIsThrown_ShouldReturnNotFoundHttpStatus() throws Exception {
//		// GIVEN
//		String sortingMethod = "asc";
//		String pageNumber = "1";
//		String pageSize = "10";
//		String statusId = "1";
//
//		// WHEN 
//		when(subcontractorService.getAllSubcontractorWithStatus(any(), anyInt(), anyInt(), anyInt()))
//		.thenThrow(new EntityNotFoundException("Il n'y a pas de sous-traitans enregistrés"));
//		
//		// THEN
//		mockMvc.perform(get(baseUrl + "/all-subcontractors/status")
//				.param("sortingMethod", sortingMethod)
//				.param("pageNumber", pageNumber)
//				.param("pageSize", pageSize)
//				.param("statusId", statusId)
//				.header("Authorization", "Bearer " + jwtToken))	
//		.andExpect(status().isNotFound());
//	}
	
	@Test
	void testGetNumberOfAllSubcontractorsWithStatus_whenRetreivingANonEmptySubcontractorsList_ShouldReturnOkHttpStatus() throws Exception {
		// GIVEN
		String statusId = "1";

		// WHEN / THEN
		mockMvc.perform(get(baseUrl + "/count-all-subcontractors/status")
				.header("Authorization", "Bearer " + jwtToken)
				.param("statusId", statusId))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$").value(30));
	}
	
//	@Test
//	void testGetNumberOfAllSubcontractorsWithStatus_whenRuntimeExceptionIsThrown_ShouldReturnBadRequestHttpStatus() throws Exception {
//		// GIVEN
//		String statusId = "1";
//
//		// WHEN
//		when(subcontractorService.getNumberOfAllNonSubcontractors())
//        .thenThrow(new RuntimeException("An exception occurred"));
//		
//		// THEN
//		mockMvc.perform(get(baseUrl + "/count-all-subcontractors")
//				.header("Authorization", "Bearer " + jwtToken))	
//		.andExpect(status().isBadRequest());
//	}
//	
//	@Test
//	void testGetNumberOfAllSubcontractorsWithStatus_whenEntityNotFoundExceptionIsThrown_ShouldReturnNotFoundHttpStatus() throws Exception {
//		// GIVEN
//	String statusId = "1";
	
//		// WHEN
//		when(subcontractorService.getNumberOfAllNonSubcontractors())
//		.thenThrow(new EntityNotFoundException("Il n'y a pas de sous-traitans enregistré"));
//		
//		// THEN
//		mockMvc.perform(get(baseUrl + "/count-all-subcontractors")
//				.header("Authorization", "Bearer " + jwtToken))	
//		.andExpect(status().isNotFound());
//	}
	
	@Test
	void testGetSubcontractor_SubcontractorOk() throws Exception {
		// GIVEN
		int expectedSId = 1;

		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/" +expectedSId)
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
	
	@Test
	void testGetSubcontractor_InvalidId_SubcontractorOk() throws Exception {
		// GIVEN
		String expectedSId = "e";
		
		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/" +expectedSId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testGetSubcontractor_InvalidIdd_SubcontractorOk() throws Exception {
		// GIVEN
		int expectedSId = -1;
		
		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/" +expectedSId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testGetSubcontractor_SubcontractorNotFound_ShouldReturnHttpNotFound() throws Exception {
		// GIVEN
		int subcontractorId = Integer.MAX_VALUE;

		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/" +subcontractorId)
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	
	@Test
	void testArchiveSubcontractor_SuccessfulArchive_ShouldReturnHttpOk() throws Exception {
		// GIVEN
		int subcontractorToArchiveId = 1;
		
		// WHEN/THEN
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
		// GIVEN
		int alreadyArchivedSubcontractorId = 103;

		// WHEN/THEN
		mockMvc.perform(
				MockMvcRequestBuilders.put(baseUrl + "/archive/" + alreadyArchivedSubcontractorId)
						.content(asJsonString(alreadyArchivedSubcontractorId))
						.header("Authorization", "Bearer " + jwtToken)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testArchiveSubcontractor_FailedArchive_InvalidId_ShouldReturnHttpNotAcceptable() throws Exception {
		// GIVEN
		String invalidId = "e";
		
		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/archive/" + invalidId)
				.header("Authorization", "Bearer " + jwtToken)
				.content(asJsonString(invalidId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable());
	}

	@Test
	void testArchiveSubcontractor_FailedArchive_NonExistingSubcontractor_ShouldReturnHttpNotFound()
			throws Exception {
		// GIVEN
		int nonExistingSubcontarctorId = Integer.MAX_VALUE;
		
		// WHEN/THEN
		mockMvc.perform(
				MockMvcRequestBuilders.put(baseUrl + "/archive/" + nonExistingSubcontarctorId)
						.content(asJsonString(nonExistingSubcontarctorId))
						.header("Authorization", "Bearer " + jwtToken)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
    @Test
    void getAllSubcontractorsBySearchAndStatus_SuccessfulWithResults() throws Exception {
		// GIVEN
    	String searchTerms = "Orang";
		String sortingMethod = "asc";
		int pageNumber = 1;
		int pageSize = 10;
		int statusId = 1;
		String columnName = "s_name";
		
		// WHEN/THEN
		mockMvc.perform(get(baseUrl + "/all-subcontractors/search")
				.param("searchTerms", searchTerms)
				.param("sortingMethod", sortingMethod)
				.param("pageNumber",String.valueOf(pageNumber))
				.param("pageSize",String.valueOf(pageSize))
				.param("statusId",String.valueOf(statusId))
				.param("columnName", columnName)
				.header("Authorization", "Bearer " + jwtToken))	
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].sName").value("Orange"))
		.andExpect(jsonPath("$[0].sEmail").value("Orange@email.fr"));
    }
    
    @Test
    void getAllSubcontractorsBySearchAndStatus_SuccessfulNoResults() throws Exception {
    	// GIVEN
    	String searchTerms = "nonExistingSubcontractor";
    	String sortingMethod = "asc";
    	int pageNumber = 1;
    	int pageSize = 10;
    	int statusId = 1;
    	String columnName = "s_name";
    	
    	// WHEN/THEN
    	mockMvc.perform(get(baseUrl + "/all-subcontractors/search")
    			.param("searchTerms", searchTerms)
    			.param("sortingMethod", sortingMethod)
    			.param("pageNumber",String.valueOf(pageNumber))
    			.param("pageSize",String.valueOf(pageSize))
    			.param("statusId",String.valueOf(statusId))
    			.param("columnName", columnName)
    			.header("Authorization", "Bearer " + jwtToken))	
    	.andExpect(status().isNotFound());
    }
    
    @Test
    void getAllSubcontractorsBySearchAndStatus_InvalidRequestMissingParameters() throws Exception {
    	// GIVEN
    	String searchTerms = null;
    	String sortingMethod = "asc";
    	int pageNumber = 1;
    	int pageSize = 10;
    	int statusId = 1;
    	String columnName = "s_name";
    	
    	// WHEN/THEN
    	mockMvc.perform(get(baseUrl + "/all-subcontractors/search")
    			.param("searchTerms", searchTerms)
    			.param("sortingMethod", sortingMethod)
    			.param("pageNumber",String.valueOf(pageNumber))
    			.param("pageSize",String.valueOf(pageSize))
    			.param("statusId",String.valueOf(statusId))
    			.param("columnName", columnName)
    			.header("Authorization", "Bearer " + jwtToken))	
    	.andExpect(status().isBadRequest());
    }
    
    @Test
    void getNumberOfSubcontractorsBySearchAndStatus_SuccessfulWithResults() throws Exception {
		// GIVEN
    	String searchTerms = "Orang";
        int statusId = 1;
        String columnName = "s_name";
		
		// WHEN/THEN
		mockMvc.perform(get(baseUrl + "/count-all-subcontractors/search")
				.param("searchTerms", searchTerms)
				.param("statusId",String.valueOf(statusId))
				.param("columnName", columnName)
				.header("Authorization", "Bearer " + jwtToken))	
		.andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1));
    }
    
    @Test
    void getNumberOfSubcontractorsBySearchAndStatus_SuccessfulNoResults() throws Exception {
    	// GIVEN
    	String searchTerms = "nonExistingSubcontractor";
        int statusId = 1;
        String columnName = "s_name";
    	
        // WHEN/THEN
        mockMvc.perform(get(baseUrl + "/count-all-subcontractors/search")
                .param("searchTerms", searchTerms)
                .param("statusId", String.valueOf(statusId))
                .param("columnName", columnName)
                .header("Authorization", "Bearer " + jwtToken))
    	.andExpect(status().isNotFound());
    }
    
    @Test
    void getNumberOfSubcontractorsBySearchAndStatus_InvalidRequestMissingParameters() throws Exception {
        // GIVEN
        String searchTerms = null;
        int statusId = 1;
        String columnName = "s_name";

        // WHEN/THEN
        mockMvc.perform(get(baseUrl + "/count-all-subcontractors/search")
                .param("searchTerms", searchTerms)
                .param("statusId", String.valueOf(statusId))
                .param("columnName", columnName)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isBadRequest());
    }


	@Test
	void testSaveSubcontractor_saveNewSubcontractor_ShouldReturnHttpCreated() throws Exception {
        // GIVEN
		Subcontractor nonExistingSubcontractorTosave = new SubcontractorBuilder()
				.withSId(99991)
				.withSName("test_saving")
				.withSEmail("test_saving@email.fr")
				.withStatus(new Status(1,"En cours"))
				.build() ;  

        // WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
				.content(asJsonString(nonExistingSubcontractorTosave))
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_saving"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("test_saving@email.fr"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(1))
				.andExpect(status().isCreated());
	}

	@Test
	void testSaveSubcontractor_UpdateExistingSubcontractor_ShouldReturnHttpOk() throws Exception {
        // GIVEN
		SubcontractorDto savedSubcontractor = new SubcontractorDtoBuilder()
				.withSId(Integer.MAX_VALUE)
				.withSName("testUpdating_0")
				.withSEmail("testUpdating_0@email.fr")
				.withStatus(new Status(1,"En cours"))
				.build();

		int savedSubcontractorId = subcontractorService.saveSubcontractor(savedSubcontractor);

		SubcontractorDto updatedSubcontractorDto = new SubcontractorDtoBuilder()
				.withSId(savedSubcontractorId)
				.withSName("testUpdating_1")
				.withSEmail("testUpdating_1@email.fr")
				.withStatus(new Status(3, "En validation"))
				.build();

        // WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
				.content(asJsonString(updatedSubcontractorDto))
                .header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("testUpdating_1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("testUpdating_1@email.fr"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(3));
	}
	
	@Test
	void testSaveSubcontractor_invalidParams_shouldReturnHttpCreated() throws Exception {
        // GIVEN
		Subcontractor nonExistingSubcontractorTosave = new SubcontractorBuilder()
				.withSId(Integer.MAX_VALUE)
				.withSName("test_saving")
				.withSEmail("test_saving@email.fr")
				.withStatus(null)
				.build() ;  

        // WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
				.content(asJsonString(nonExistingSubcontractorTosave))
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void testSaveSubcontractor_invalidId_shouldReturnHttpCreated() throws Exception {
		// GIVEN
		Subcontractor nonExistingSubcontractorTosave = new SubcontractorBuilder()
				.withSId(-1)
				.withSName("test_saving")
				.withSEmail("test_saving@email.fr")
				.withStatus(null)
				.build() ;  
		
		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
				.content(asJsonString(nonExistingSubcontractorTosave))
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testSaveSubcontractor_DuplicateData_shouldReturnHttpCreated() throws Exception {
		// GIVEN
		SubcontractorDto nonExistingSubcontractorTosave = new SubcontractorDtoBuilder()
				.withSId(Integer.MAX_VALUE)
				.withSName("Orange")
				.withSEmail("Orange@email.fr")
				.withStatus(new Status(1,"En cours"))
				.build() ;  
		
		// WHEN/THEN
		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
				.content(asJsonString(nonExistingSubcontractorTosave))
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());
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
