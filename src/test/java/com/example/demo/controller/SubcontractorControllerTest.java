package com.example.demo.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.service.SubcontractorService;

@SpringBootTest
@AutoConfigureMockMvc
public class SubcontractorControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private SubcontractorService service;
	
	@InjectMocks
    private SubcontractorController subcontractorController;
	
	
	@Test
	public void getAllSubcontractorAPI() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/subcontractor/getAll?nameColonne=s_id&sorting=desc&pageSize=1&page=10")
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        ;
					
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
}
