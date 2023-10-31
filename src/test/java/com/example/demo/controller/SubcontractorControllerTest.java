package com.example.demo.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SubcontractorControllerTest {
//	@Autowired
//	private MockMvc mvc;
//
//	@Autowired
//	private SubcontractorService subcontractorService;
//	
//    @InjectMocks
//    private SubcontractorController subcontractorController;
//
//	private final String baseUrl = "/subcontractor/";
//	
//    @Test
//    public void testGetSubcontractor_ValidId() {
//        // Setup
//        int validId = 1;
//        Subcontractor subcontractor = new Subcontractor();
//        Mockito.when(subcontractorService.getSubcontractorWithStatus(validId)).thenReturn(subcontractor);
//
//        // Test
//        ResponseEntity<?> responseEntity = subcontractorController.getSubcontractor(String.valueOf(validId));
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(subcontractor, responseEntity.getBody());
//    }
//
//	@Test
//	public void testGetSubcontractor_SubcontractorFound() throws Exception {
//		int subcontractorId = 1;
//		String expectedName = "Orange";
//		String expectedEmail = "Orange@email.com";
//		int expectedStatusId = 1;
//		String expectedStatusName = "EN_COURS";
//		String expectedStatusDescription = "AAAA";
//
//		mvc.perform(MockMvcRequestBuilders.get(baseUrl + subcontractorId)
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk()).andExpect(jsonPath("$.sId").value(subcontractorId))
//		.andExpect(jsonPath("$.sName").value(expectedName)).andExpect(jsonPath("$.sEmail").value(expectedEmail))
//		.andExpect(jsonPath("$.status.stId").value(expectedStatusId))
//		.andExpect(jsonPath("$.status.stName").value(expectedStatusName))
//		.andExpect(jsonPath("$.status.stDescription").value(expectedStatusDescription));
//	}
//
//	@Test
//	public void testGetSubcontractor_SubcontractorNotFound() throws Exception {
//		Status statusForTest = new Status(1, "EN_COURS", "AAAA");
//		SubcontractorDto savedSubcontractorDto = new SubcontractorDto();
//		savedSubcontractorDto.setSName("test_savinSb");
//		savedSubcontractorDto.setSEmail("test_savinSb@test_savinSb.com");
//		savedSubcontractorDto.setStatus(statusForTest);
//
//		mvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
//				.content(asJsonString(savedSubcontractorDto))
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isCreated())
//		.andExpect(MockMvcResultMatchers.jsonPath("$.sId").exists());
//	}
//
//	@Test
//	public void testSaveSubcontractor_saveNewSubcontractor() throws Exception {
//		Subcontractor savedSubcontractor = new Subcontractor(0, "test_updating_Sb_0",
//				"test_updating_Sb_0@test_updating_Sb.com_0", new Status(1, "EN_COURS", "AAAA"));
//		int savedSubcontractorId = subcontractorService.saveSubcontractor(savedSubcontractor); // persister le
//																								// sous-traitant et
//																								// recuperer son vrai id
//		mvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
//				.content(asJsonString(new SubcontractorDto(savedSubcontractorId, "test_updating_Sb_1",
//						"test_updating_Sb_1@email.com", new Status(2, "EN_VALIDATION", "BBBB"))))
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_updating_Sb_1"));
//	}
//
//	@Test
//	public void testSaveSubcontractor_UpdateExistingSubcontractor() throws Exception {
//		Subcontractor savedSubcontractor = new Subcontractor(0, "test_updating_Sb_0",
//				"test_updating_Sb_0@test_updating_Sb.com_0", new Status(1, "EN_COURS", "AAAA"));
//		int savedSubcontractorId = subcontractorService.saveSubcontractor(savedSubcontractor);
//
//		SubcontractorDto updatedSubcontractorDto = new SubcontractorDto(savedSubcontractorId, "test_updating_Sb_1",
//				"test_updating_Sb_1@email.com", new Status(2, "EN_VALIDATION", "BBBB"));
//
//		mvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save")
//				.content(asJsonString(updatedSubcontractorDto))
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_updating_Sb_1"));
//	}
//
//	//method pour convertir un objet Java en sa représentation JSON sous forme de chaîne de caractères.
//	public static String asJsonString(Object object) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			return objectMapper.writeValueAsString(object);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
}
