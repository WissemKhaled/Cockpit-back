package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;

@Component
public class SubcontractorDtoMapper {
	public SubcontractorDto subcontractorToDto(Subcontractor subcontractor) {
		return new SubcontractorDto(subcontractor.getId(),subcontractor.getName(), subcontractor.getEmail(), subcontractor.getSubcontractorStatus());
	}
	
	public Subcontractor dtoToSubcontractor(SubcontractorDto subcontractorDto) {
		return new Subcontractor(subcontractorDto.getId(),subcontractorDto.getName(),subcontractorDto.getEmail(),subcontractorDto.getSubcontractorStatus());
	}
}
