package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;

@Component
public class ServiceProviderDtoMapper {

	public ServiceProviderDto serviceProviderToDto(ServiceProvider serviceProviderEntity) {
		ServiceProviderDto serviceProviderDto = new ServiceProviderDto();
		serviceProviderDto.setSpId(serviceProviderEntity.getSpId());
		serviceProviderDto.setSpFirstName(serviceProviderEntity.getSpFirstName());
		serviceProviderDto.setSpName(serviceProviderEntity.getSpName());
		serviceProviderDto.setSpEmail(serviceProviderEntity.getSpEmail());
		serviceProviderDto.setSpCreationDate(serviceProviderEntity.getSpCreationDate());
		serviceProviderDto.setSpLastUpdateDate(serviceProviderEntity.getSpLastUpdateDate());
		serviceProviderDto.setStatus(serviceProviderEntity.getSpStatus());
		serviceProviderDto.setSubcontractor(serviceProviderEntity.getSubcontractor());
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
		serviceProvider.setSpStatus(serviceProviderDto.getStatus());
		serviceProvider.setSubcontractor(serviceProviderDto.getSubcontractor());
		return serviceProvider;
	}

}
