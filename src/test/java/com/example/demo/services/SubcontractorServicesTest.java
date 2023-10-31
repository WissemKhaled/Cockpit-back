package com.example.demo.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.SubcontractorService;

public class SubcontractorServicesTest {
	
	@MockBean
	private SubcontractorService subcontractorService;
	
	@Mock
	private SubcontractorMapper mapper;
	
	@Mock
	private SubcontractorDtoMapper dtoMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@Test
	public void testGetAllSubcontractor() throws Exception{
		
		 mockMvc.perform(get("/getAll"))
         .andExpect(status().isOk());
	}
	
}
