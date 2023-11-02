package com.example.demo.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@TestInstance(Lifecycle.PER_CLASS) // on utilise un @BeforeAll (@AfterAll) non-statique et la methodes qui lui corresponde, donc on a besoin de changer test instance lifecycle vers per_class.
public class SubcontractorMapperTest {

	@Autowired
	private SubcontractorMapper subcontractorMapper;

	@BeforeAll
	void init() {
		Subcontractor SubcontractorToSave = new Subcontractor(2, "ArchiveTest", "ArchiveTest@email.fr",
				new Status(1, "EN_COURS", "AAAA"));
		subcontractorMapper.insertSubcontractor(SubcontractorToSave);
	}

	@Test
	void archiveTest_ArchivingASubcontractor_ShouldReturnOne() {
		Subcontractor existingSubcontractor = new Subcontractor(2, "ArchiveTest", "ArchiveTest@email.fr",
				new Status(1, "EN_COURS", "AAAA"));
		int isArchived = subcontractorMapper.archive(existingSubcontractor);
		assertEquals(isArchived, 1);
	}

	@Test
	void archiveTest_ArchivingASubcontractorFailed_ShouldReturnZero() {
		Subcontractor nonExistingSubcontractor = new Subcontractor(Integer.MAX_VALUE, "ArchiveTest",
				"ArchiveTest@email.fr", new Status(1, "EN_COURS", "AAAA"));
		int isNotArchived = subcontractorMapper.archive(nonExistingSubcontractor);
		assertEquals(isNotArchived, 0);
	}
}
