package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.mappers.ServiceProviderMapper;

@SpringBootTest
@Transactional
public class ServiceProviderServiceTest {

	@InjectMocks
	private ServiceProviderServiceImpl serviceProviderService;

	@Mock
	private ServiceProviderMapper serviceProviderMapper;

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsSaved_thenReturnOne() {
		ServiceProvider serviceProviderToSave = new ServiceProvider();
		serviceProviderToSave.setSpId(10);
		serviceProviderToSave.setSpFirstName("sp-first-name");
		serviceProviderToSave.setSpName("sp-name");
		serviceProviderToSave.setSpEmail("sp-email");

		given(serviceProviderMapper.insert(serviceProviderToSave)).willReturn(1);

		int savedId = serviceProviderService.saveServiceProvider(serviceProviderToSave);

		assertEquals(serviceProviderToSave.getSpId(), savedId);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotSaved_thenReturnZero() {
		ServiceProvider serviceProviderToSave = new ServiceProvider();
		serviceProviderToSave.setSpId(10);
		serviceProviderToSave.setSpFirstName("sp-first-name");
		serviceProviderToSave.setSpName("sp-name");
		serviceProviderToSave.setSpEmail("sp-email");
		
		given(serviceProviderMapper.insert(serviceProviderToSave)).willReturn(0);
		
		int savedId = serviceProviderService.saveServiceProvider(serviceProviderToSave);
		
		assertEquals(0, savedId);
	}
}
