package com.example.demo.dto.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.demo.builder.SubcontractorBuilder;
import com.example.demo.builder.SubcontractorDtoBuilder;
import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.service.ServiceProviderService;

@Component
public class SubcontractorDtoMapper {
	@Autowired
	@Lazy  //ici j'utilise @autowired et @Lazy pour rompre le dépendance circulaire
	private ServiceProviderDtoMapper serviceProviderDtoMapper;
	
	@Autowired
	@Lazy //ici j'utilise @autowired et @Lazy pour rompre le dépendance circulaire
	private ServiceProviderService serviceProviderService;

	public SubcontractorDto subcontractorToDto(Subcontractor subcontractor) {
		return new SubcontractorDtoBuilder()
				.withSId(subcontractor.getSId())
				.withSName(subcontractor.getSName())
				.withSEmail(subcontractor.getSEmail())
				.withSCreationDate(subcontractor.getSCreationDate())
				.withSLastUpdateDate(subcontractor.getSLastUpdateDate())
				.withStatus(subcontractor.getStatus())
				.withServiceProvidersIds(serviceProviderService.getServiceProvidersBySubcontractorId(subcontractor.getSId()).stream()
						.map(ServiceProviderDto::getSpId).toList())
				.build();
	}

	public Subcontractor dtoToSubcontractor(SubcontractorDto subcontractorDto) {
		return new SubcontractorBuilder()
				.withSId(subcontractorDto.getSId())
				.withSName(subcontractorDto.getSName())
				.withSEmail(subcontractorDto.getSEmail())
				.withSCreationDate(subcontractorDto.getSCreationDate())
				.withSLastUpdateDate(subcontractorDto.getSLastUpdateDate())
				.withStatus(subcontractorDto.getStatus())
				.withServiceProviders(serviceProviderService.getServiceProvidersBySubcontractorId(subcontractorDto.getSId()).stream()
						.map(serviceProviderDtoMapper::dtoToserviceProvider).toList())
				.build();
	}
}