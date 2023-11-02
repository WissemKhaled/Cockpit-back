package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.SubcontractorServiceImpl;

@SpringBootTest
public class SubcontractorServicesTest {

	@InjectMocks
	private SubcontractorServiceImpl subcontractorService;

	@Mock
	private SubcontractorMapper mapper;

	@Mock
	private SubcontractorDtoMapper dtoMapper;

	@Test
	public void testGetAllSubcontractorWithData() {
		// Mock data
		String nameColonne = "s_id";
		String sorting = "asc";
		int page = 2;
		int pageSize = 2;

		List<Subcontractor> mockSubcontractors = new ArrayList<>();
		mockSubcontractors.add(new Subcontractor(1, "roger", "hh@outlook.fr", 1));
	
		List<SubcontractorDto> mockDtos = new ArrayList<>();
		mockDtos.add(new SubcontractorDto(1, "roger", "hh@outlook.fr", 1));

		// Mock the behavior of subcontractorMapper
		when(mapper.getAllSubcontractors(nameColonne, sorting, page, pageSize)).thenReturn(mockSubcontractors);
		
		// Mock the behavior of dtoMapper
		when(dtoMapper.subcontractorToDto(any(Subcontractor.class))).thenReturn(mockDtos.get(0));
	
		List<SubcontractorDto> result = subcontractorService.getAllSubcontractor(nameColonne, sorting, page, pageSize);
		
		assertEquals(1, result.size());
	}
	
    @Test
    public void testGetAllSubcontractorWithNoData() {
       
        String nameColonne = "s_id";
        String sorting = "asc";
        int page = 1;
        int pageSize = 10;

        // Configurez le mock subcontractorMapper pour renvoyer une liste vide
        when(mapper.getAllSubcontractors(nameColonne, sorting, 0, pageSize))
            .thenReturn(Collections.emptyList());

        // Appelez la méthode que vous testez et vérifiez qu'elle génère une RuntimeException
        assertThrows(RuntimeException.class, () -> {
            subcontractorService.getAllSubcontractor(nameColonne, sorting, page, pageSize);
        });
    }

}
