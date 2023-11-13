package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SubcontractorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private SubcontractorService subcontractorService;
	
	@Mock
	private SubcontractorService service;

	private String baseUrl = "/subcontractor/";


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
	public void testGetSubcontractor_SubcontractorOk() throws Exception {
		int expectedSId = 1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime expectedSCreationDate = LocalDateTime.parse("2023-01-01 12:00:00", formatter);
		DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + expectedSId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sId").value(expectedSId))
				.andExpect(jsonPath("$.sName").value("Subcontractor 1"))
				.andExpect(jsonPath("$.sEmail").value("subcontractor1@example.com"))
				.andExpect(jsonPath("$.sCreationDate").value(expectedSCreationDate.format(responseFormatter)))
				.andExpect(jsonPath("$.sLastUpdateDate").isEmpty())
				.andExpect(jsonPath("$.status.stId").value(1))
				.andExpect(jsonPath("$.status.stName").value("En cours"));
		}

	@Test
	public void testGetSubcontractor_SubcontractorNotFound_ShouldReturnHttpNotFound() throws Exception {
		int subcontractorId = Integer.MAX_VALUE;

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + subcontractorId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testSaveSubcontractor_saveNewSubcontractor_ShouldReturnHttpOk() throws Exception {
		Subcontractor nonExistingSubcontractorTosave = new Subcontractor() ;  
		nonExistingSubcontractorTosave.setSId(99991);
		nonExistingSubcontractorTosave.setSName("test_saving");
		nonExistingSubcontractorTosave.setSEmail("test_saving@email.fr");
		nonExistingSubcontractorTosave.setStatus(new Status(1));

		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save").content(asJsonString(nonExistingSubcontractorTosave))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("test_saving"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("test_saving@email.fr"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(1))
				.andExpect(status().isCreated());
	}

	@Test
	public void testSaveSubcontractor_UpdateExistingSubcontractor_ShouldReturnHttpOk() throws Exception {
		Subcontractor savedSubcontractor = new Subcontractor();
		savedSubcontractor.setSId(Integer.MAX_VALUE);
		savedSubcontractor.setSName("testUpdating_0");
		savedSubcontractor.setSEmail("testUpdating_0@email.fr");
		savedSubcontractor.setStatus(new Status(1));
		int savedSubcontractorId = subcontractorService.saveSubcontractor(savedSubcontractor);

		SubcontractorDto updatedSubcontractorDto = new SubcontractorDto();
		updatedSubcontractorDto.setSId(savedSubcontractorId);
		updatedSubcontractorDto.setSName("testUpdating_1");
		updatedSubcontractorDto.setSEmail("testUpdating_1@email.fr");
		updatedSubcontractorDto.setStatus(new Status(3));

		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/save").content(asJsonString(updatedSubcontractorDto))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.sName").value("testUpdating_1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.sEmail").value("testUpdating_1@email.fr"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(3));
	}

	@Test
	public void testArchiveSubcontractor_SuccessfulArchive_ShouldReturnHttpOk() throws Exception {
		int subcontractorToArchiveId = 1;

		mockMvc.perform(MockMvcRequestBuilders
				.put(baseUrl + "/archive/" + subcontractorToArchiveId)
				.content(asJsonString(subcontractorToArchiveId)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stId").value(4))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status.stName").value("Archivé"));
	}

	@Test
	public void testArchiveSubcontractor_FailedArchive_ShouldReturnAlreadyArchivedSubcontractorException()
			throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.put(baseUrl + "/archive/" + 2)
						.content(asJsonString(2)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testArchiveSubcontractor_FailedArchive_InvalidId_ShouldReturnHttpNotAcceptable() throws Exception {
		String invalidId = "e";
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/archive/" + invalidId)
				.content(asJsonString(invalidId)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable());
	}

	@Test
	public void testArchiveSubcontractor_FailedArchive_NonExistingSubcontractor_ShouldReturnHttpNotFound()
			throws Exception {
		int nonExistingSubcontarctorId = Integer.MAX_VALUE - 3942;
		mockMvc.perform(
				MockMvcRequestBuilders.put(baseUrl + "/archive/" + nonExistingSubcontarctorId)
						.content(asJsonString(nonExistingSubcontarctorId)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

// method pour convertir un objet Java en sa représentation JSON sous forme de chaîne de caractères.
	public static String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
