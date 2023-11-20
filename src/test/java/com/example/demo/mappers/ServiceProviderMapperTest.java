package com.example.demo.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

@SpringBootTest
public class ServiceProviderMapperTest {
	
	@Autowired
	private ServiceProviderMapper serviceProviderMapper;

	@Test
	void archiveTest_ArchivingServiceProvider_ShouldReturnOne() {
		
		Subcontractor existingSubcontractor = new Subcontractor();
		existingSubcontractor.setSId(1);

		ServiceProvider existingServiceProvider = new ServiceProvider();
		existingServiceProvider.setSpId(1);
		existingServiceProvider.setSpName("prenom-prestator-1");
		existingServiceProvider.setSpFirstName("nom-prestateur-1");
		existingServiceProvider.setSpEmail("prestateur@email.com");
		existingServiceProvider.setSpCreationDate(LocalDateTime.now());
		existingServiceProvider.setSubcontractor(existingSubcontractor);
		existingServiceProvider.setStatus(new Status(1));
		
		int isArchived = serviceProviderMapper.archive(existingServiceProvider);
		
		assertEquals(1, isArchived);
	}
	
	@Test
	void archiveTest_ArchivingServiceProviderFailed_ShouldReturnZero() {
		
		Subcontractor ExistingSubcontractor = new Subcontractor();
		ExistingSubcontractor.setSId(1);

		ServiceProvider nonExistingServiceProvider = new ServiceProvider();
		nonExistingServiceProvider.setSpId(Integer.MAX_VALUE);
		nonExistingServiceProvider.setSpName("prenom-prestator-1");
		nonExistingServiceProvider.setSpFirstName("nom-prestateur-1");
		nonExistingServiceProvider.setSpEmail("prestateur@email.com");
		nonExistingServiceProvider.setSpCreationDate(LocalDateTime.now());
		nonExistingServiceProvider.setSubcontractor(ExistingSubcontractor);
		nonExistingServiceProvider.setStatus(new Status(1));
		
		int isArchived = serviceProviderMapper.archive(nonExistingServiceProvider);
		
		assertEquals(0, isArchived);
	}
}
