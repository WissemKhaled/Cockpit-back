package com.example.demo.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.builder.SubcontractorBuilder;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

@SpringBootTest
@Transactional
public class SubcontractorMapperTest {

	@Autowired
	private SubcontractorMapper subcontractorMapper;

	@Test
	void archiveTest_ArchivingASubcontractor_ShouldReturnOne() {
		
		// GIVEN
		Subcontractor existingSubcontractor = new SubcontractorBuilder()
				.withSId(1)
				.withSName("Orange")
				.withSEmail("Orange@email.fr")
				.withStatus(new Status(1))
				.build();

		// WHEN
		int isArchived = subcontractorMapper.archiveSubcontractor(existingSubcontractor);
		
		// THEN
		assertEquals(1, isArchived);
	}

	@Test
	void archiveTest_ArchivingASubcontractorFailed_ShouldReturnZero() {
		
		// GIVEN
		Subcontractor nonExistingSubcontractor = new SubcontractorBuilder()
				.withSId(Integer.MAX_VALUE)
				.withSName("NonExistingSubcontractor")
				.withSEmail("NonExistingSubcontractor@example.fr")
				.withStatus(new Status(1))
				.build();
		
		// WHEN
		int isNotArchived = subcontractorMapper.archiveSubcontractor(nonExistingSubcontractor);
		
		// THEN
		assertEquals(0, isNotArchived);
	}
	
    @Test
    public void testCountAllNonArchivedSubcontractors_ShouldReturnExpectedCount() {

		// GIVEN
    	int expectedCount = 5;
    	
		// WHEN
        int count = subcontractorMapper.countAllNonArchivedSubcontractors();

		// THEN
        assertEquals(expectedCount, count); // Replace expectedCount with the expected result
    }
    
    @Test
    public void testCountAllNonArchivedSubcontractorsWithStatus() {
    	
		// GIVEN
    	int expectedCount = 5;
    	
		// WHEN
        int count = subcontractorMapper.countAllNonArchivedSubcontractorsWithStatus(1);
        
		// THEN
        assertEquals(expectedCount, count);
    }
    
}
