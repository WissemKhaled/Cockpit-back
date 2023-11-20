package com.example.demo.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
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
		
		Subcontractor ExistingSubcontractor = new Subcontractor();
		ExistingSubcontractor.setSId(1);

		ServiceProvider ExistingServiceProvider = new ServiceProvider();
		ExistingServiceProvider.setSpId(1);
		ExistingServiceProvider.setSpName("prenom-prestator-1");
		ExistingServiceProvider.setSpFirstName("nom-prestateur-1");
		ExistingServiceProvider.setSpCreationDate(LocalDateTime.now());
		ExistingServiceProvider.setSubcontractor(ExistingSubcontractor);
		ExistingServiceProvider.setStatus(new Status(1));
		
		int isArchived = serviceProviderMapper.archive(ExistingServiceProvider);
		
		assertEquals(0, isArchived);
	}
}
