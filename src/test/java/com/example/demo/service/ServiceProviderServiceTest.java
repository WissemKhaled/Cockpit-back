package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.ServiceProviderNotFoundException;
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
		serviceProviderToSave.setSpEmail("sp@email.com");

		given(serviceProviderMapper.insertServiceProvider(serviceProviderToSave)).willReturn(1);

		int savedId = serviceProviderService.saveServiceProvider(serviceProviderToSave);

		assertEquals(serviceProviderToSave.getSpId(), savedId);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotSaved_thenReturnZero() {
		ServiceProvider serviceProviderToSave = new ServiceProvider();
		serviceProviderToSave.setSpId(10);
		serviceProviderToSave.setSpFirstName("sp-first-name");
		serviceProviderToSave.setSpName("sp-name");
		serviceProviderToSave.setSpEmail("sp@email.com");

		given(serviceProviderMapper.insertServiceProvider(serviceProviderToSave)).willReturn(0);

		int savedId = serviceProviderService.saveServiceProvider(serviceProviderToSave);

		assertEquals(0, savedId);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsArchived_thenReturnOne() {
		ServiceProvider serviceProviderToArchive = new ServiceProvider();
		serviceProviderToArchive.setSpId(10);
		serviceProviderToArchive.setSpFirstName("sp-first-name");
		serviceProviderToArchive.setSpName("sp-name");
		serviceProviderToArchive.setSpEmail("sp@email.com");
		serviceProviderToArchive.setSpStatus(new Status(1));

		given(serviceProviderMapper.archiveServiceProvider(serviceProviderToArchive)).willReturn(1);

		int isArchived = serviceProviderService.archiveServiceProvider(serviceProviderToArchive);

		assertEquals(1, isArchived);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotArchived_thenReturnZero() {
		ServiceProvider serviceProviderToArchive = new ServiceProvider();
		serviceProviderToArchive.setSpId(10);
		serviceProviderToArchive.setSpFirstName("sp-first-name");
		serviceProviderToArchive.setSpName("sp-name");
		serviceProviderToArchive.setSpEmail("sp@email.com");
		serviceProviderToArchive.setSpStatus(new Status(1));

		given(serviceProviderMapper.archiveServiceProvider(serviceProviderToArchive)).willReturn(0);

		int isArchived = serviceProviderService.archiveServiceProvider(serviceProviderToArchive);

		assertEquals(0, isArchived);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsUpdated_thenReturnOne() {
		ServiceProvider serviceProviderToUpdate = new ServiceProvider();
		serviceProviderToUpdate.setSpId(10);
		serviceProviderToUpdate.setSpFirstName("sp-first-name");
		serviceProviderToUpdate.setSpName("sp-name");
		serviceProviderToUpdate.setSpEmail("sp@email.com");
		serviceProviderToUpdate.setSpStatus(new Status(1));

		given(serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate)).willReturn(1);

		int isUpdated = serviceProviderService.updateServiceProvider(serviceProviderToUpdate);

		assertEquals(1, isUpdated);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotUpdated_thenReturnZero() {
		ServiceProvider serviceProviderToUpdate = new ServiceProvider();
		serviceProviderToUpdate.setSpId(10);
		serviceProviderToUpdate.setSpFirstName("sp-first-name");
		serviceProviderToUpdate.setSpName("sp-name");
		serviceProviderToUpdate.setSpEmail("sp@email.com");
		serviceProviderToUpdate.setSpStatus(new Status(1));

		given(serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate)).willReturn(0);

		int isUpdated = serviceProviderService.updateServiceProvider(serviceProviderToUpdate);

		assertEquals(0, isUpdated);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsFound_thenReturnTheServiceProvider() {
		ServiceProvider serviceProviderToBeFound = new ServiceProvider();
		serviceProviderToBeFound.setSpId(1);
		serviceProviderToBeFound.setSpFirstName("sp-1-first_name");
		serviceProviderToBeFound.setSpName("sp-1-name");
		serviceProviderToBeFound.setSpEmail("sp1@email.com");
		serviceProviderToBeFound.setSubcontractor(new Subcontractor("sp-1-name", "sp1@email.com"));
		serviceProviderToBeFound.setSpStatus(new Status(3));

		given(serviceProviderMapper.findServiceProviderById(serviceProviderToBeFound.getSpId()))
				.willReturn(serviceProviderToBeFound);

		ServiceProvider foundedServiceProvider = serviceProviderService
				.getServiceProviderById(serviceProviderToBeFound.getSpId());

		assertEquals(serviceProviderToBeFound, foundedServiceProvider);
	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotFound_thenReturnNull() {
		int nonExistingServiceProviderId = Integer.MAX_VALUE - 554;
		
        ServiceProviderNotFoundException thrown = assertThrows(ServiceProviderNotFoundException.class, () -> {
        	serviceProviderService.getServiceProviderById(nonExistingServiceProviderId);
        });
        assertEquals(String.format("le prestataire avec l'id: %d n'existe pas!!",nonExistingServiceProviderId), thrown.getMessage());
	}

	@Test
	public void getTest_GetServiceProvidersBySubcontractorId_ShouldReturnTwo() {
		int existingSubcontractorId = 1;
		List<ServiceProvider> expectedServiceProviders = new ArrayList<ServiceProvider>();

		expectedServiceProviders.add(new ServiceProvider(1, "sp-1-first_name", "sp-1-name", "sp1@email.com",
				LocalDateTime.now(), null, new Subcontractor(1), new Status(3)));
		expectedServiceProviders.add(new ServiceProvider(2, "sp-2-first_name", "sp-2-name", "sp2@email.com",
				LocalDateTime.now(), null, new Subcontractor(1), new Status(1)));

		given(serviceProviderMapper.findServiceProvidersBySubcontractorId(anyInt()))
				.willReturn(expectedServiceProviders);

		List<ServiceProvider> serviceProviders = serviceProviderService
				.getServiceProvidersBySubcontractorId(existingSubcontractorId);

		assertEquals(2, serviceProviders.size());
	}

	@Test
	public void getTest_GetServiceProvidersBySubcontractorIdFailed_ShouldReturnZero() {
		try {
			int existingSubcontractorId = 1;
			List<ServiceProvider> expectedServiceProviders = new ArrayList<ServiceProvider>();

			given(serviceProviderMapper.findServiceProvidersBySubcontractorId(existingSubcontractorId))
					.willReturn(expectedServiceProviders);

			serviceProviderService.getServiceProvidersBySubcontractorId(existingSubcontractorId);

		} catch (ServiceProviderNotFoundException e) {
			assert (e.getMessage().equals("le sous-traitant avec l'id: 1 n'a pas de prestataires"));
		}

	}

	@Test
	public void testCheckIfServiceProviderExistsWhenExists() {
		given(serviceProviderMapper.findServiceProviderById(anyInt())).willReturn(new ServiceProvider());

		boolean result = serviceProviderService.checkIfServiceProviderExist(1);

		assertTrue(result);

		verify(serviceProviderMapper).findServiceProviderById(1);
	}
	
	@Test
	public void testCheckIfServiceProviderExistsWhenNotExists() {
		given(serviceProviderMapper.findServiceProviderById(anyInt())).willReturn(null);

		boolean result = serviceProviderService.checkIfServiceProviderExist(1);

		assertFalse(result);

		verify(serviceProviderMapper).findServiceProviderById(1);
	}
}
