package com.example.demo.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

@SpringBootTest
public class SubcontractorMapperTest {

	@Autowired
	private SubcontractorMapper subcontractorMapper;

	@Test
	void archiveTest_ArchivingASubcontractor_ShouldReturnOne() {
		Subcontractor existingSubcontractor = new Subcontractor();
		existingSubcontractor.setSId(1);
		existingSubcontractor.setSName("Subcontractor 1");
		existingSubcontractor.setSEmail("Subcontractor1@example.com");
		existingSubcontractor.setStatus(new Status(1));

		int isArchived = subcontractorMapper.archiveSubcontractor(existingSubcontractor);
		assertEquals(1, isArchived);
	}

	@Test
	void archiveTest_ArchivingASubcontractorFailed_ShouldReturnZero() {
		Subcontractor nonExistingSubcontractor = new Subcontractor();
		nonExistingSubcontractor.setSId(Integer.MAX_VALUE);
		nonExistingSubcontractor.setSName("NonExistingSubcontractor");
		nonExistingSubcontractor.setSEmail("NonExistingSubcontractor@example.fr");
		nonExistingSubcontractor.setStatus(new Status(1));

		int isNotArchived = subcontractorMapper.archiveSubcontractor(nonExistingSubcontractor);
		assertEquals(0, isNotArchived);
	}
}
