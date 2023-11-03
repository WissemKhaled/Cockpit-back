package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.service.SubcontractorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SubcontractorControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private SubcontractorService subcontractorService;

	@Autowired
	private MockMvc mockMvc;
	private String baseUrl = "/subcontractor/";

	@InjectMocks
	private SubcontractorController subcontractorController;

	@Mock
	private SubcontractorService service;

	@Test
	public void getAllSubcontractorAPI() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get("/subcontractor/getAll?nameColonne=s_id&sorting=desc&pageSize=1&page=10")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void getAllSubcontractorAPI_isEmpty() throws Exception {
		
        when(service.getAllSubcontractor(any(), any(), anyInt(), anyInt()))
        .thenThrow(new RuntimeException("An exception occurred"));
		
	       mockMvc.perform(get("/subcontractor/getAll")
	                .param("nameColonne", "s_id")
	                .param("sorting", "asc")
	                .param("pageSize", "10")
	                .param("page", "1"))
	                .andExpect(status().isBadRequest());
	}

	@BeforeEach
	void init() {
		Subcontractor savedSubcontractorForTesting = new Subcontractor(2, "Orange", "Orange@email.fr",
				new Status(1, "EN_COURS", "AAAA"));
		subcontractorService.saveSubcontractor(savedSubcontractorForTesting);
	}

	@Test
	public void testGetSubcontractor_SubcontractorFound() throws Exception {
		int subcontractorId = 2;
		String expectedName = "Orange";
		String expectedEmail = "Orange@email.fr";
		int expectedStatusId = 1;
		String expectedStatusName = "EN_COURS";
		String expectedStatusDescription = "AAAA";

		mvc.perform(MockMvcRequestBuilders.get(baseUrl + subcontractorId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound()).andExpect(jsonPath("$.sId").value(subcontractorId))
				.andExpect(jsonPath("$.sName").value(expectedName)).andExpect(jsonPath("$.sEmail").value(expectedEmail))
				.andExpect(jsonPath("$.status.stId").value(expectedStatusId))
				.andExpect(jsonPath("$.status.stName").value(expectedStatusName))
				.andExpect(jsonPath("$.status.stDescription").value(expectedStatusDescription));
	}

	@Test
	public void testGetSubcontractor_SubcontractorNotFound() throws Exception {
		int subcontractorId = Integer.MAX_VALUE;

		mvc.perform(MockMvcRequestBuilders.get(baseUrl + subcontractorId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testSaveSubcontractor_saveNewSubcontractor() throws Exception {
		Subcontractor savedSubcontractor = new Subcontractor(1, "test_saving", "test_saving@email.fr",
				new Status(1, "EN_COURS", "AAAA"));

		mvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save").content(asJsonString(savedSubcontractor))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_saving"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("test_saving@email.fr"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stName").value("EN_COURS"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stDescription").value("AAAA"));
//		.andExpect(status().isCreated());
	}

	@Test
	public void testSaveSubcontractor_UpdateExistingSubcontractor() throws Exception {
		Subcontractor savedSubcontractor = new Subcontractor(Integer.MAX_VALUE, "test_updating_Sb_0",
				"test_updating_Sb_0@test_updating_Sb.com_0", new Status(1, "EN_COURS", "AAAA"));
		int savedSubcontractorId = subcontractorService.saveSubcontractor(savedSubcontractor);

		SubcontractorDto updatedSubcontractorDto = new SubcontractorDto(savedSubcontractorId, "test_updating_Sb_1",
				"test_updating_Sb_1@email.com", new Status(2, "EN_VALIDATION", "BBBB"));

		mvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save").content(asJsonString(updatedSubcontractorDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_updating_Sb_1"));
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
}
