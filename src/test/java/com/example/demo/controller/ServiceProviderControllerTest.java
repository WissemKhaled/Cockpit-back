package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.service.ServiceProviderService;

@SpringBootTest
public class ServiceProviderControllerTest {
	
	private String baseUrl = "/service-providers/";

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private ServiceProviderService serProviderService;
	
	
	@Test
	public void testGetServiceProvider_ShouldReturnHttpStatusOk() throws Exception {
		int expectedSpId = 1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime expectedSCreationDate = LocalDateTime.parse("2023-01-01 12:00:00", formatter);
		DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + expectedSpId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.spId").value(expectedSpId))
				.andExpect(jsonPath("$.spFirstName").value("sp-1-name"))
				.andExpect(jsonPath("$.spName").value("sp-1-first-name"))
				.andExpect(jsonPath("$.spEmail").value("sp-1-email"))
				.andExpect(jsonPath("$.spCreationDate").value(expectedSCreationDate.format(responseFormatter)))
				.andExpect(jsonPath("$.spLastUpdateDate").isEmpty())
				.andExpect(jsonPath("$.subcontractor.spId").value(1))
				.andExpect(jsonPath("$.status.stId").value(3));
		}
}
