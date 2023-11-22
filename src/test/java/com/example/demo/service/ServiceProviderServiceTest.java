package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

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

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsFound_thenReturnTheServiceProvider() {
		ServiceProvider serviceProviderToBeFound = new ServiceProvider();
		serviceProviderToBeFound.setSpId(1);
		serviceProviderToBeFound.setSpFirstName("sp-first-name");
		serviceProviderToBeFound.setSpName("sp-name");
		serviceProviderToBeFound.setSpEmail("sp-email");
		serviceProviderToBeFound.setSubcontractor(new Subcontractor("sp-1-name", "sp-1-email"));
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

		given(serviceProviderMapper.findServiceProviderById(anyInt())).willReturn(null);

		ServiceProvider nonFoundServiceProvider = serviceProviderService
				.getServiceProviderById(nonExistingServiceProviderId);

		assertEquals(null, nonFoundServiceProvider);
	}

	@Test
	public void getTest_GetServiceProvidersBySubcontractorId_ShouldReturnTwo() {
		int existingSubcontractorId = 1;
		List<ServiceProvider> expectedServiceProviders = new ArrayList<ServiceProvider>();

		expectedServiceProviders.add(new ServiceProvider(1, "sp-1-first_name", "sp-1-name", "sp-1-email",
				LocalDateTime.now(), null, new Subcontractor(1), new Status(3)));
		expectedServiceProviders.add(new ServiceProvider(2, "sp-2-first_name", "sp-2-name", "sp-2-email",
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
}
