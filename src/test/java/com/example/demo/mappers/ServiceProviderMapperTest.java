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

//	@Test
//	void findTest_FindingServiceProvider_ShouldReturnOne() {
//		int existingSubcontractorId = 1;
//
//		List<ServiceProvider> serviceProviders = serviceProviderMapper
//				.findServiceProvidersBySubcontractorId(existingSubcontractorId);
//		assertEquals(1, serviceProviders.size());
//	}
//	
//	@Test
//	void findTest_FindingServiceProviderFailed_ShouldReturnZero() {
//		int nonExistingSubcontractorId = Integer.MAX_VALUE;
//
//		List<ServiceProvider> serviceProviders = serviceProviderMapper
//				.findServiceProvidersBySubcontractorId(nonExistingSubcontractorId);
//
//		assertEquals(0, serviceProviders.size());
//	}
//
//	@Test
//	void archiveTest_ArchivingServiceProvider_ShouldReturnOne() {
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(1);
//
//		ServiceProvider existingServiceProvider = new ServiceProvider();
//		existingServiceProvider.setSpId(1);
//		existingServiceProvider.setSpName("prenom-prestator-1");
//		existingServiceProvider.setSpFirstName("nom-prestateur-1");
//		existingServiceProvider.setSpEmail("prestateur@email.com");
//		existingServiceProvider.setSpCreationDate(LocalDateTime.now());
//		existingServiceProvider.setSubcontractor(existingSubcontractor);
//		existingServiceProvider.setStatus(new Status(1));
//
//		int isArchived = serviceProviderMapper.archive(existingServiceProvider);
//
//		assertEquals(1, isArchived);
//	}
//
//	@Test
//	void archiveTest_ArchivingServiceProviderFailed_ShouldReturnZero() {
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(1);
//
//		ServiceProvider nonExistingServiceProvider = new ServiceProvider();
//		nonExistingServiceProvider.setSpId(Integer.MAX_VALUE);
//		nonExistingServiceProvider.setSpName("prenom-prestator-1");
//		nonExistingServiceProvider.setSpFirstName("nom-prestateur-1");
//		nonExistingServiceProvider.setSpEmail("prestateur@email.com");
//		nonExistingServiceProvider.setSpCreationDate(LocalDateTime.now());
//		nonExistingServiceProvider.setSubcontractor(existingSubcontractor);
//		nonExistingServiceProvider.setStatus(new Status(1));
//
//		int isArchived = serviceProviderMapper.archive(nonExistingServiceProvider);
//
//		assertEquals(0, isArchived);
//	}
//
//	@Test
//	void insertTest_InsertingServiceProvider_ShouldReturnOne() {
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(1);
//
//		ServiceProvider serviceProvidertoSave = new ServiceProvider();
//		serviceProvidertoSave.setSpName("prenom-prestator-2");
//		serviceProvidertoSave.setSpFirstName("nom-prestateur-2");
//		serviceProvidertoSave.setSpEmail("prestateur2@email.com");
//		serviceProvidertoSave.setSpCreationDate(LocalDateTime.now());
//		serviceProvidertoSave.setSubcontractor(existingSubcontractor);
//		serviceProvidertoSave.setStatus(new Status(1));
//
//		int isInserted = serviceProviderMapper.insert(serviceProvidertoSave);
//
//		assertEquals(1, isInserted);
//
//	}

//	@Test
//	void insertTest_InsertingServiceProviderFailed_ShouldReturnZero() {
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(1);
//		
//		ServiceProvider serviceProvidertoSave = new ServiceProvider();
//		serviceProvidertoSave.setSpName("");
//		serviceProvidertoSave.setSpFirstName("");
//		serviceProvidertoSave.setSpEmail("prestateur2@email.com");
//		serviceProvidertoSave.setSpCreationDate(LocalDateTime.now());
//		serviceProvidertoSave.setSubcontractor(existingSubcontractor);
//		serviceProvidertoSave.setStatus(new Status(5));
//		
//		int isInserted = serviceProviderMapper.insert(serviceProvidertoSave);
//		
//		assertEquals(1, isInserted);
//	}
	
//	@Test
//	void updateTest_UpdateServiceProvider_ShouldReturnOne() {
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(2);
//		
//		ServiceProvider existingServiceProvider = new ServiceProvider();
//		existingServiceProvider.setSpId(1);
//		existingServiceProvider.setSpName("sp1");
//		existingServiceProvider.setSpFirstName("sp1_name");
//		existingServiceProvider.setSpEmail("sp1_email");
//		existingServiceProvider.setSubcontractor(existingSubcontractor);
//		existingServiceProvider.setStatus(new Status(4));
//		
//		int isUpdated = serviceProviderMapper.update(existingServiceProvider);
//		
//		assertEquals(1, isUpdated);
//	}
//	
//	@Test
//	void updateTest_UpdateServiceProvider_ShouldReturnZero() {
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(2);
//		
//		ServiceProvider existingServiceProvider = new ServiceProvider();
//		existingServiceProvider.setSpId(Integer.MAX_VALUE);
//		existingServiceProvider.setSpName("sp1");
//		existingServiceProvider.setSpFirstName("sp1_name");
//		existingServiceProvider.setSpEmail("sp1_email");
//		existingServiceProvider.setSubcontractor(existingSubcontractor);
//		existingServiceProvider.setStatus(new Status(4));
//		
//		int isUpdated = serviceProviderMapper.update(existingServiceProvider);
//		
//		assertEquals(0, isUpdated);
//	}

}
