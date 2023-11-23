package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.service.StatusService;
import com.example.demo.service.SubcontractorService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ServiceProviderDtoMapper {
	
	private final StatusService statusService;
	private final SubcontractorService subcontractorService;

	public ServiceProviderDto serviceProviderToDto(ServiceProvider serviceProvider) {
		ServiceProviderDto serviceProviderDto = new ServiceProviderDto();
		serviceProviderDto.setSpId(serviceProvider.getSpId());
		serviceProviderDto.setSpFirstName(serviceProvider.getSpFirstName());
		serviceProviderDto.setSpName(serviceProvider.getSpName());
		serviceProviderDto.setSpEmail(serviceProvider.getSpEmail());
		serviceProviderDto.setSpCreationDate(serviceProvider.getSpCreationDate());
		serviceProviderDto.setSpLastUpdateDate(serviceProvider.getSpLastUpdateDate());
		serviceProviderDto.setSpStatutId(serviceProvider.getSpStatus().getStId());
		serviceProviderDto.setSubcontractorId(serviceProvider.getSubcontractor().getSId());
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
		serviceProvider.setSpStatus(statusService.getStatusById(serviceProviderDto.getSpStatutId()));
		serviceProvider.setSubcontractor(subcontractorService.getSubcontractorWithStatus(serviceProviderDto.getSubcontractorId()));
		return serviceProvider;
	}

}
