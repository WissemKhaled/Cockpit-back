package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

@SpringBootTest
@Transactional
public class ServiceProviderDtoMapperTest {
	
	@Autowired
	private ServiceProviderDtoMapper serviceProviderDtoMapper;
	
	@Test
	void whenConvertServiceProviderEntityToServiceProviderDto_thenCorrect() {
		ServiceProvider serviceProviderEntity = new ServiceProvider();
		serviceProviderEntity.setSpId(10);
		serviceProviderEntity.setSpFirstName("sp_first_name");
		serviceProviderEntity.setSpName("sp_name");
		serviceProviderEntity.setSpEmail("sp_email");
		serviceProviderEntity.setSpCreationDate(LocalDateTime.now());
		serviceProviderEntity.setSpLastUpdateDate(LocalDateTime.now());
		serviceProviderEntity.setSpStatus(new Status(1));
		serviceProviderEntity.setSubcontractor(new Subcontractor());
		
		ServiceProviderDto serviceProviderDto = serviceProviderDtoMapper.serviceProviderToDto(serviceProviderEntity);
		
		assertEquals(serviceProviderEntity.getSpId(), serviceProviderDto.getSpId());
		assertEquals(serviceProviderEntity.getSpFirstName(), serviceProviderDto.getSpFirstName());
		assertEquals(serviceProviderEntity.getSpName(), serviceProviderDto.getSpName());
		assertEquals(serviceProviderEntity.getSpEmail(), serviceProviderDto.getSpEmail());
		assertEquals(serviceProviderEntity.getSpCreationDate(), serviceProviderDto.getSpCreationDate());
		assertEquals(serviceProviderEntity.getSpLastUpdateDate(), serviceProviderDto.getSpLastUpdateDate());
		assertEquals(serviceProviderEntity.getSpStatus().getStId(), serviceProviderDto.getSpStatutId());
		assertEquals(serviceProviderEntity.getSubcontractor().getSId(), serviceProviderDto.getSubcontractorId());
	}
	
	@Test
	void whenConvertServiceProviderDtoToServiceProviderEntity_thenCorrect() {
		ServiceProviderDto serviceProviderDto = new ServiceProviderDto();
		serviceProviderDto.setSpId(10);
		serviceProviderDto.setSpFirstName("sp_first_name");
		serviceProviderDto.setSpName("sp_name");
		serviceProviderDto.setSpEmail("sp_email");
		serviceProviderDto.setSpCreationDate(LocalDateTime.now());
		serviceProviderDto.setSpLastUpdateDate(LocalDateTime.now());
		serviceProviderDto.setSpStatutId(1);
		serviceProviderDto.setSubcontractorId(1);
		
		ServiceProvider serviceProvider = serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDto);
		
		assertEquals(serviceProviderDto.getSpId(), serviceProvider.getSpId());
		assertEquals(serviceProviderDto.getSpFirstName(), serviceProvider.getSpFirstName());
		assertEquals(serviceProviderDto.getSpName(), serviceProvider.getSpName());
		assertEquals(serviceProviderDto.getSpEmail(), serviceProvider.getSpEmail());
		assertEquals(serviceProviderDto.getSpCreationDate(), serviceProvider.getSpCreationDate());
		assertEquals(serviceProviderDto.getSpLastUpdateDate(), serviceProvider.getSpLastUpdateDate());
		assertEquals(serviceProviderDto.getSpStatutId(), serviceProvider.getSpStatus().getStId());
		assertEquals(serviceProviderDto.getSubcontractorId(), serviceProvider.getSubcontractor().getSId());
	}
}
