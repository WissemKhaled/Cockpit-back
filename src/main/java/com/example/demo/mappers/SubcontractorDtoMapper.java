package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.service.StatusService;

import lombok.AllArgsConstructor;



@Component
@AllArgsConstructor
public class SubcontractorDtoMapper {
	
	private final StatusService statusService;
	
	public SubcontractorDto subcontractorToDto(Subcontractor subcontractor) {
		return new SubcontractorDto(subcontractor.getSId(), subcontractor.getSName(), subcontractor.getSEmail(),
				subcontractor.getStatus());
	}

	public Subcontractor dtoToSubcontractor(SubcontractorDto subcontractorDto) {
		System.err.println(subcontractorDto.getStatus().toString());
		System.err.println(statusService.getStatusById(subcontractorDto.getStatus().getStId()));
		return new Subcontractor(subcontractorDto.getSId(), subcontractorDto.getSName(), subcontractorDto.getSEmail(),
				statusService.getStatusById(subcontractorDto.getStatus().getStId()));
	}
}
