package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.implementation.ServiceProviderServiceImpl;

@SpringBootTest
@Transactional
public class ServiceProviderServiceTest {

	@InjectMocks
	private ServiceProviderServiceImpl serviceProviderService;

	@Mock
	private ServiceProviderMapper serviceProviderMapper;

//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsSaved_thenReturnOne() {
//		ServiceProvider serviceProviderToSave = new ServiceProvider();
//		serviceProviderToSave.setSpId(10);
//		serviceProviderToSave.setSpFirstName("Spfirstname");
//		serviceProviderToSave.setSpName("SPNAME");
//		serviceProviderToSave.setSpEmail("Sp@email.com");
//
//		given(serviceProviderMapper.insertServiceProvider(serviceProviderToSave)).willReturn(1);
//
//		int savedId = serviceProviderService.saveServiceProvider(serviceProviderToSave);
//
//		assertEquals(serviceProviderToSave.getSpId(), savedId);
//	}
//
//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsNotSaved_thenReturnZero() {
//		ServiceProvider serviceProviderToSave = new ServiceProvider();
//		serviceProviderToSave.setSpId(10);
//		serviceProviderToSave.setSpFirstName("Spfirstname");
//		serviceProviderToSave.setSpName("SPNAME");
//		serviceProviderToSave.setSpEmail("Sp@email.com");
//
//		given(serviceProviderMapper.insertServiceProvider(serviceProviderToSave)).willReturn(0);
//
//		int savedId = serviceProviderService.saveServiceProvider(serviceProviderToSave);
//
//		assertEquals(0, savedId);
//	}
//	
//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsArchived_thenReturnOne() {
//		ServiceProvider serviceProviderToArchive = new ServiceProvider();
//		serviceProviderToArchive.setSpId(10);
//		serviceProviderToArchive.setSpFirstName("Spfirstname");
//		serviceProviderToArchive.setSpName("SPNAME");
//		serviceProviderToArchive.setSpEmail("Sp@email.com");
//		serviceProviderToArchive.setSpStatus(new Status(1));
// 
//		given(serviceProviderMapper.archiveServiceProvider(serviceProviderToArchive)).willReturn(1);
// 
//		int isArchived = serviceProviderService.archiveServiceProvider(serviceProviderToArchive);
// 
//		assertEquals(1, isArchived);
//	}
// 
//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsNotArchived_thenReturnZero() {
//		ServiceProvider serviceProviderToArchive = new ServiceProvider();
//		serviceProviderToArchive.setSpId(10);
//		serviceProviderToArchive.setSpFirstName("Spfirstname");
//		serviceProviderToArchive.setSpName("SPNAME");
//		serviceProviderToArchive.setSpEmail("Sp@email.com");
//		serviceProviderToArchive.setSpStatus(new Status(1));
// 
//		given(serviceProviderMapper.archiveServiceProvider(serviceProviderToArchive)).willReturn(0);
// 
//		int isArchived = serviceProviderService.archiveServiceProvider(serviceProviderToArchive);
// 
//		assertEquals(0, isArchived);
//	}
//	
//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsUpdated_thenReturnOne() {
//		ServiceProvider serviceProviderToUpdate = new ServiceProvider();
//		serviceProviderToUpdate.setSpId(10);
//		serviceProviderToUpdate.setSpFirstName("Spfirstname");
//		serviceProviderToUpdate.setSpName("SPNAME");
//		serviceProviderToUpdate.setSpEmail("Sp@email.com");
//		serviceProviderToUpdate.setSpStatus(new Status(1));
//
//		given(serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate)).willReturn(1);
//
//		int isUpdated = serviceProviderService.updateServiceProvider(serviceProviderToUpdate);
//
//		assertEquals(1, isUpdated);
//	}
//
//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsNotUpdated_thenReturnZero() {
//		ServiceProvider serviceProviderToUpdate = new ServiceProvider();
//		serviceProviderToUpdate.setSpId(10);
//		serviceProviderToUpdate.setSpFirstName("Spfirstname");
//		serviceProviderToUpdate.setSpName("SPNAME");
//		serviceProviderToUpdate.setSpEmail("Sp@email.com");
//		serviceProviderToUpdate.setSpStatus(new Status(1));
//
//		given(serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate)).willReturn(0);
//
//		int isUpdated = serviceProviderService.updateServiceProvider(serviceProviderToUpdate);
//
//		assertEquals(0, isUpdated);
//	}
//
//	@Test
//	public void givenServiceProviderEntity_whenServicePeroviderIsFound_thenReturnTheServiceProvider() {
//		ServiceProvider serviceProviderToBeFound = new ServiceProvider();
//		serviceProviderToBeFound.setSpId(1);
//		serviceProviderToBeFound.setSpFirstName("Spfirstname");
//		serviceProviderToBeFound.setSpName("SPNAME");
//		serviceProviderToBeFound.setSpEmail("Sp1@email.com");
//		serviceProviderToBeFound.setSubcontractor(new Subcontractor("sname", "s1@email.com"));
//		serviceProviderToBeFound.setSpStatus(new Status(3));
//
//		given(serviceProviderMapper.findServiceProviderWithSubcontractorBySpId(serviceProviderToBeFound.getSpId()))
//				.willReturn(serviceProviderToBeFound);
//		
//		ServiceProvider foundedServiceProvider = serviceProviderService.getServiceProviderById(serviceProviderToBeFound.getSpId());
//
//		assertEquals(serviceProviderToBeFound, foundedServiceProvider);
//	}

	@Test
	public void givenServiceProviderEntity_whenServicePeroviderIsNotFound_thenReturnNull() {
		int nonExistingServiceProviderId = Integer.MAX_VALUE - 554;
		
		EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
        	serviceProviderService.getServiceProviderById(nonExistingServiceProviderId);
        });
        assertEquals(String.format("le prestataire avec l'id: %d n'existe pas!!",nonExistingServiceProviderId), thrown.getMessage());
	}

//	@Test
//	public void getTest_GetServiceProvidersBySubcontractorId_ShouldReturnTwo() {
//		int existingSubcontractorId = 1;
//		List<ServiceProvider> expectedServiceProviders = new ArrayList<ServiceProvider>();
//
//		expectedServiceProviders.add(new ServiceProvider(1, "Firstspfirstname", "FIRSTSPNAME", "Sp1@email.com",
//				LocalDateTime.now(), null, new Subcontractor(1), new Status(3)));
//		expectedServiceProviders.add(new ServiceProvider(2, "Secondspfirstname", "SECONDESPNAME", "Sp2@email.com",
//				LocalDateTime.now(), null, new Subcontractor(1), new Status(1)));
//
//		given(serviceProviderMapper.findServiceProvidersBySubcontractorId(anyInt()))
//				.willReturn(expectedServiceProviders);
//
//		List<ServiceProvider> serviceProviders = serviceProviderService
//				.getServiceProvidersBySubcontractorId(existingSubcontractorId);
//
//		assertEquals(2, serviceProviders.size());
//	}

	@Test
	public void getTest_GetServiceProvidersBySubcontractorIdFailed_ShouldReturnZero() {
		try {
			int existingSubcontractorId = 1;
			List<ServiceProvider> expectedServiceProviders = new ArrayList<ServiceProvider>();

			given(serviceProviderMapper.findServiceProvidersBySubcontractorId(existingSubcontractorId))
					.willReturn(expectedServiceProviders);

			serviceProviderService.getServiceProvidersBySubcontractorId(existingSubcontractorId);

		} catch (EntityNotFoundException e) {
			assert (e.getMessage().equals("le sous-traitant avec l'id: 1 n'a pas de prestataires"));
		}

	}

	@Test
	public void testCheckIfServiceProviderExistsWhenExists() {
		given(serviceProviderMapper.findServiceProviderById(anyInt())).willReturn(new ServiceProvider());

		boolean result = serviceProviderService.checkIfServiceProviderExistById(1);

		assertTrue(result);

		verify(serviceProviderMapper).findServiceProviderById(1);
	}
	
	@Test
	public void testCheckIfServiceProviderExistsWhenNotExists() {
		given(serviceProviderMapper.findServiceProviderById(anyInt())).willReturn(null);

		boolean result = serviceProviderService.checkIfServiceProviderExistById(1);

		assertFalse(result);

		verify(serviceProviderMapper).findServiceProviderById(1);
	}

	@Test
	public void checkIfServiceProviderExistBySpEmail_ExistingEmail_ShouldReturnId() {
		// Arrange
		String email = "Sp1@email.com";
		ServiceProvider existingServiceProvider = new ServiceProvider();
		existingServiceProvider.setSpId(1);

		given(serviceProviderMapper.findServiceProviderBySpEmail(email)).willReturn(existingServiceProvider);

		int result = serviceProviderService.checkIfSubcontractorExistBySpEmail(email);

		assertEquals(1, result);
	}

	@Test
	public void checkIfServiceProviderExistBySpEmail_NonExistingEmail_ShouldReturnZero() {
		// Arrange
		String email = "Nonexistent@email.com";

		given(serviceProviderMapper.findServiceProviderBySpEmail(email)).willReturn(null);

		int result = serviceProviderService.checkIfSubcontractorExistBySpEmail(email);

		assertEquals(0, result);
	}

	@Test
	public void handleServiceProviderSaving_ExistingEmailAndDifferentId_ShouldThrowException() {
		// Arrange
		ServiceProviderDto serviceProviderDto = new ServiceProviderDto();
		serviceProviderDto.setSpId(99);
		serviceProviderDto.setSpEmail("Sp1@email.com");

		ServiceProvider existingServiceProvider = new ServiceProvider(1);
		existingServiceProvider.setSpEmail("Sp1@email.com");

		given(serviceProviderMapper.findServiceProviderBySpEmail(serviceProviderDto.getSpEmail()))
				.willReturn(existingServiceProvider);

		assertThrows(EntityDuplicateDataException.class, () -> {
			serviceProviderService.handleServiceProviderSaving(serviceProviderDto);
		});
	}

	@Test
	public void handleServiceProviderUpdating_ExistingEmail_ShouldThrowException() {
		// Arrange
		ServiceProviderDto serviceProviderDto = new ServiceProviderDto();
		serviceProviderDto.setSpId(1);
		serviceProviderDto.setSpEmail("Sp2@email.com");

		ServiceProvider existingServiceProvider = new ServiceProvider(2);
		existingServiceProvider.setSpEmail("Sp2@email.com");

		given(serviceProviderMapper.findServiceProviderBySpEmail(serviceProviderDto.getSpEmail()))
				.willReturn(existingServiceProvider);

		assertThrows(EntityDuplicateDataException.class, () -> {
			serviceProviderService.handleServiceProviderUpdating(serviceProviderDto);
		});
	}
}