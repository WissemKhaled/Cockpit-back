package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;

@Component
public class SubcontractorDtoMapper {
	public SubcontractorDto subcontractorToDto(Subcontractor subcontractor) {
		return new SubcontractorDto(subcontractor.getSId(),subcontractor.getSName(), subcontractor.getSEmail(), subcontractor.getSFkStatusId());
	}
	
	public Subcontractor dtoToSubcontractor(SubcontractorDto subcontractorDto) {
		return new Subcontractor(subcontractorDto.getSId(),subcontractorDto.getSName(),subcontractorDto.getSEmail(),subcontractorDto.getSFkStatusId());
	}
}
 