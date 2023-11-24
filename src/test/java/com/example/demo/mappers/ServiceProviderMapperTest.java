package com.example.demo.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

@SpringBootTest
@Transactional
public class ServiceProviderMapperTest {

	@Autowired
	private ServiceProviderMapper serviceProviderMapper;

	@Test
	void findTest_FindingServiceProvidersBySubcontractorId_ShouldReturnTwo() {
		int existingSubcontractorId = 1;

		List<ServiceProvider> serviceProviders = serviceProviderMapper
				.findServiceProvidersBySubcontractorId(existingSubcontractorId);
		assertEquals(2, serviceProviders.size());

	}

	@Test
	void findTest_FindingServiceProvidersBySubcontractorIdFailed_ShouldReturnZero() {
		int nonExistingSubcontractorId = Integer.MAX_VALUE;

		List<ServiceProvider> serviceProviders = serviceProviderMapper
				.findServiceProvidersBySubcontractorId(nonExistingSubcontractorId);

		assertEquals(0, serviceProviders.size());
	}
	
	@Test
	void findTest_FindingServiceProviderByServiceProviderId_ShouldReturnTheServiceProviderIdAndItsFirstName() {
		int existingServiceProviderId = 1;
		
		ServiceProvider foundedServiceProvider = serviceProviderMapper.findServiceProviderById(existingServiceProviderId);

		assertEquals(1, foundedServiceProvider.getSpId());
		assertEquals("sp-1-first_name", foundedServiceProvider.getSpFirstName());

	}
	
	@Test
	void findTest_FindingServiceProviderByServiceProviderIdFailed_ShouldReturnNull() {
		int nonExistingServiceProviderId = Integer.MAX_VALUE-1000;
		
		ServiceProvider nonFoundedServiceProvider = serviceProviderMapper.findServiceProviderById(nonExistingServiceProviderId);

		assertEquals(null, nonFoundedServiceProvider);

	}

	@Test
	void findTest_FindingServiceProviderByServiceProviderIdFailed_ShouldReturnZero() {
		int nonExistingSubcontractorId = Integer.MAX_VALUE;

		List<ServiceProvider> serviceProviders = serviceProviderMapper
				.findServiceProvidersBySubcontractorId(nonExistingSubcontractorId);

		assertEquals(0, serviceProviders.size());
	}

	@Test
	void archiveTest_ArchivingServiceProvider_ShouldReturnOne() {
		Subcontractor existingSubcontractor = new Subcontractor();
		existingSubcontractor.setSId(1);

		ServiceProvider existingServiceProvider = new ServiceProvider();
		existingServiceProvider.setSpId(1);
		existingServiceProvider.setSpName("sp-1-first_name");
		existingServiceProvider.setSpFirstName("sp-1-nom");
		existingServiceProvider.setSpEmail("sp@email.com");
		existingServiceProvider.setSpCreationDate(LocalDateTime.now());
		existingServiceProvider.setSubcontractor(existingSubcontractor);
		existingServiceProvider.setSpStatus(new Status(1));

		int isArchived = serviceProviderMapper.archiveServiceProvider(existingServiceProvider);

		assertEquals(1, isArchived);
	}

	@Test
	void archiveTest_ArchivingServiceProviderFailed_ShouldReturnZero() {
		Subcontractor existingSubcontractor = new Subcontractor();
		existingSubcontractor.setSId(1);

		ServiceProvider nonExistingServiceProvider = new ServiceProvider();
		nonExistingServiceProvider.setSpId(Integer.MAX_VALUE);
		nonExistingServiceProvider.setSpName("sp-999-name");
		nonExistingServiceProvider.setSpFirstName("sp-999-first_name");
		nonExistingServiceProvider.setSpEmail("sp999@email.com");
		nonExistingServiceProvider.setSpCreationDate(LocalDateTime.now());
		nonExistingServiceProvider.setSubcontractor(existingSubcontractor);
		nonExistingServiceProvider.setSpStatus(new Status(1));

		int isArchived = serviceProviderMapper.archiveServiceProvider(nonExistingServiceProvider);

		assertEquals(0, isArchived);
	}

	@Test
	void insertTest_InsertingServiceProvider_ShouldReturnOne() {
		Subcontractor existingSubcontractor = new Subcontractor();
		existingSubcontractor.setSId(1);

		ServiceProvider serviceProvidertoSave = new ServiceProvider();
		serviceProvidertoSave.setSpName("sp-5-name");
		serviceProvidertoSave.setSpFirstName("sp-5-first_name");
		serviceProvidertoSave.setSpEmail("sp5@email.com");
		serviceProvidertoSave.setSpCreationDate(LocalDateTime.now());
		serviceProvidertoSave.setSubcontractor(existingSubcontractor);
		serviceProvidertoSave.setSpStatus(new Status(1));

		int isInserted = serviceProviderMapper.insertServiceProvider(serviceProvidertoSave);

		assertEquals(1, isInserted);

	}

	@Test
	void updateTest_UpdateServiceProvider_ShouldReturnOne() {
		Subcontractor existingSubcontractor = new Subcontractor();
		existingSubcontractor.setSId(1);

		ServiceProvider existingServiceProvider = new ServiceProvider();
		existingServiceProvider.setSpId(1);
		existingServiceProvider.setSpName("sp-5-name");
		existingServiceProvider.setSpFirstName("sp-5-first_name");
		existingServiceProvider.setSpEmail("sp5@email.com");
		existingServiceProvider.setSubcontractor(existingSubcontractor);
		existingServiceProvider.setSpStatus(new Status(2));

		int isUpdated = serviceProviderMapper.updateServiceProvider(existingServiceProvider);

		assertEquals(1, isUpdated);
	}

}
