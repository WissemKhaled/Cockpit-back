package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
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
	
	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsArchived_thenReturnOne() {
		ServiceProvider serviceProviderToArchive = new ServiceProvider();
		serviceProviderToArchive.setSpId(10);
		serviceProviderToArchive.setSpFirstName("sp-first-name");
		serviceProviderToArchive.setSpName("sp-name");
		serviceProviderToArchive.setSpEmail("sp-email");
		serviceProviderToArchive.setSpStatus(new Status(1));

		given(serviceProviderMapper.archive(serviceProviderToArchive)).willReturn(1);

		int isArchived = serviceProviderService.archiveServiceProvider(serviceProviderToArchive);

		assertEquals(1, isArchived);
	}
	
	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotArchived_thenReturnZero() {
		ServiceProvider serviceProviderToArchive = new ServiceProvider();
		serviceProviderToArchive.setSpId(10);
		serviceProviderToArchive.setSpFirstName("sp-first-name");
		serviceProviderToArchive.setSpName("sp-name");
		serviceProviderToArchive.setSpEmail("sp-email");
		serviceProviderToArchive.setSpStatus(new Status(1));

		given(serviceProviderMapper.archive(serviceProviderToArchive)).willReturn(0);

		int isArchived = serviceProviderService.archiveServiceProvider(serviceProviderToArchive);

		assertEquals(0, isArchived);
	}
	
	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsUpdated_thenReturnOne() {
		ServiceProvider serviceProviderToUpdate = new ServiceProvider();
		serviceProviderToUpdate.setSpId(10);
		serviceProviderToUpdate.setSpFirstName("sp-first-name");
		serviceProviderToUpdate.setSpName("sp-name");
		serviceProviderToUpdate.setSpEmail("sp-email");
		serviceProviderToUpdate.setSpStatus(new Status(1));

		given(serviceProviderMapper.update(serviceProviderToUpdate)).willReturn(1);

		int isUpdated = serviceProviderService.updateServiceProvider(serviceProviderToUpdate);

		assertEquals(1, isUpdated);
	}
	
	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotUpdated_thenReturnZero() {
		ServiceProvider serviceProviderToUpdate = new ServiceProvider();
		serviceProviderToUpdate.setSpId(10);
		serviceProviderToUpdate.setSpFirstName("sp-first-name");
		serviceProviderToUpdate.setSpName("sp-name");
		serviceProviderToUpdate.setSpEmail("sp-email");
		serviceProviderToUpdate.setSpStatus(new Status(1));

		given(serviceProviderMapper.update(serviceProviderToUpdate)).willReturn(0);

		int isUpdated = serviceProviderService.updateServiceProvider(serviceProviderToUpdate);

		assertEquals(0, isUpdated);
	}
}
