package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.service.StatusService;
import com.example.demo.service.SubcontractorService;

@Component
public class ServiceProviderDtoMapper {
	
	private final StatusService statusService;
	private final SubcontractorService subcontractorService;
	
	public ServiceProviderDtoMapper(StatusService statusService, SubcontractorService subcontractorService) {
		this.statusService = statusService;
		this.subcontractorService = subcontractorService;
	}

	public ServiceProviderDto serviceProviderToDto(ServiceProvider serviceProvider) {
		ServiceProviderDto serviceProviderDto = new ServiceProviderDto();
		serviceProviderDto.setSpId(serviceProvider.getSpId());
		serviceProviderDto.setSpFirstName(serviceProvider.getSpFirstName());
		serviceProviderDto.setSpName(serviceProvider.getSpName());
		serviceProviderDto.setSpEmail(serviceProvider.getSpEmail());
		serviceProviderDto.setSpCreationDate(serviceProvider.getSpCreationDate());
		serviceProviderDto.setSpLastUpdateDate(serviceProvider.getSpLastUpdateDate());
		serviceProviderDto.setSpStatus(serviceProvider.getSpStatus());
		serviceProviderDto.setSubcontractorSName(serviceProvider.getSubcontractor().getSName());
		return serviceProviderDto;
	}

	public ServiceProvider dtoToserviceProvider(ServiceProviderDto serviceProviderDto) {
		ServiceProvider serviceProvider = new ServiceProvider();
		serviceProvider.setSpId(serviceProviderDto.getSpId());
		serviceProvider.setSpFirstName(serviceProviderDto.getSpFirstName());
		serviceProvider.setSpName(serviceProviderDto.getSpName());
		serviceProvider.setSpEmail(serviceProviderDto.getSpEmail());
		serviceProvider.setSpCreationDate(serviceProviderDto.getSpCreationDate());
		serviceProvider.setSpLastUpdateDate(serviceProviderDto.getSpLastUpdateDate());
		serviceProvider.setSpStatus(statusService.getStatusById(serviceProviderDto.getSpStatus().getStId()));
		serviceProvider.setSubcontractor(subcontractorService.getSubcontractorBySName(serviceProviderDto.getSubcontractorSName()));
		return serviceProvider;
	}

}
