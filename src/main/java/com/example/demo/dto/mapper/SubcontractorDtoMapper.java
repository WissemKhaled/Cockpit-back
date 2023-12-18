package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Subcontractor;
import com.example.demo.service.ServiceProviderService;
import com.example.demo.service.StatusService;

@Component
public class SubcontractorDtoMapper {

	private final StatusService statusService;
	private final ServiceProviderService serviceProviderService;

	public SubcontractorDtoMapper(StatusService statusService, ServiceProviderService serviceProviderService) {
		this.statusService = statusService;
		this.serviceProviderService = serviceProviderService;
	}

	public SubcontractorDto subcontractorToDto(Subcontractor subcontractor) {
		return new SubcontractorDto(subcontractor.getSId(), subcontractor.getSName(), subcontractor.getSEmail(),
				subcontractor.getSCreationDate(), subcontractor.getSLastUpdateDate(), subcontractor.getStatus(),
				serviceProviderService.getServiceProvidersBySubcontractorId(subcontractor.getSId()).stream()
						.map(ServiceProvider::getSpId).toList());
	}

	public Subcontractor dtoToSubcontractor(SubcontractorDto subcontractorDto) {
		return new Subcontractor(subcontractorDto.getSId(), subcontractorDto.getSName(), subcontractorDto.getSEmail(),
				subcontractorDto.getSCreationDate(), subcontractorDto.getSLastUpdateDate(),
				statusService.getStatusById(subcontractorDto.getStatus().getStId()),
				serviceProviderService.getServiceProvidersBySubcontractorId(subcontractorDto.getSId()));
	}
}