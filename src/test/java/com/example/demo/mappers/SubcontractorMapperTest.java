package com.example.demo.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

	// test[MethodName]_[Conditions]_[ExpectedBehavior]

	@Autowired
	private SubcontractorMapper subcontractorMapper;

	@Test
	void archiveTest_ArchivingASubcontractor_ShouldReturnOne() {

		// GIVEN
		Subcontractor existingSubcontractor = new SubcontractorBuilder().withSId(1).withSName("Orange")
				.withSEmail("Orange@email.fr").withStatus(new Status(1)).build();

		// WHEN
		int isArchived = subcontractorMapper.archiveSubcontractor(existingSubcontractor);

		// THEN
		assertEquals(1, isArchived);
	}

	@Test
	void archiveTest_ArchivingASubcontractorFailed_ShouldReturnZero() {

		// GIVEN
		Subcontractor nonExistingSubcontractor = new SubcontractorBuilder().withSId(Integer.MAX_VALUE)
				.withSName("NonExistingSubcontractor").withSEmail("NonExistingSubcontractor@example.fr")
				.withStatus(new Status(1)).build();

		// WHEN
		int isNotArchived = subcontractorMapper.archiveSubcontractor(nonExistingSubcontractor);

		// THEN
		assertEquals(0, isNotArchived);
	}

	@Test
	public void testCountAllNonArchivedSubcontractors_ShouldReturnExpectedCount() {

		// GIVEN
		int expectedCount = 101;

		// WHEN
		int count = subcontractorMapper.countAllNonArchivedSubcontractors();

		// THEN
		assertEquals(expectedCount, count); // Replace expectedCount with the expected result
	}

	@Test
	public void testCountAllNonArchivedSubcontractorsWithStatus_StatusSelectedWithIdEqualsOne_ShouldReturnThirty() {

		// GIVEN
		int expectedCount = 30;

		// WHEN
		int count = subcontractorMapper.countAllNonArchivedSubcontractorsWithStatus(1);

		// THEN
		assertEquals(expectedCount, count);
	}

	@Test
	public void testFindAllNonArchivedSubcontractors_10ItemsSortedByStatusColumnInAscendingOrder_ShouldReturn10Subcontractors() {

		// GIVEN
		String columnNameForSorting = "s_fk_status_id";
		String sortinMethod = "ASC";

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(columnNameForSorting,
				sortinMethod, 0, 10);

		// THEN
		assertNotNull(subcontractors);
		assertFalse(subcontractors.isEmpty());
		assertThat(subcontractors).hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Alstom");
	}

	@Test
	public void testFindAllNonArchivedSubcontractors_10ItemsSortedByStatusColumnInDescendingOrder_ShouldReturn10Subcontractors() {

		// GIVEN
		String columnNameForSorting = "s_fk_status_id";
		String sortinMethod = "DESC";

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(columnNameForSorting,
				sortinMethod, 0, 10);

		// THEN
		assertNotNull(subcontractors);
		assertFalse(subcontractors.isEmpty());
		assertThat(subcontractors).hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("VirtuosoSoft Solutions");
	}

	@Test
	public void testFindAllNonArchivedSubcontractorsSortedByNameColumnDescendingOrder() {

		// GIVEN
		String columnNameForSorting = "s_email";
		String sortinMethod = "ASC";

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(columnNameForSorting,
				sortinMethod, 0, 10);

		// THEN
		assertNotNull(subcontractors);
		assertFalse(subcontractors.isEmpty());
		assertThat(subcontractors).hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Alstom");
	}

	@Test
	public void testFindAllNonArchivedSubcontractors_20ItemsSortedByStatusColumnInAscendingOrder_ShouldReturn20Subcontractors() {

		// GIVEN
		String columnNameForSorting = "s_fk_status_id";
		String sortinMethod = "ASC";
		int pageSize = 20;
		int offset = 0; // la première page

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(columnNameForSorting,
				sortinMethod, offset, pageSize);

		// THEN
		assertNotNull(subcontractors);
		assertFalse(subcontractors.isEmpty());
		assertThat(subcontractors).hasSize(20);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Alstom");
	}

	@Test
	public void testFindAllNonArchivedSubcontractors_10ItemsSortedByStatusColumnInAscendingOrder_ShouldReturn10SubcontractorInSecondPage() {

		// GIVEN
		String columnNameForSorting = "s_fk_status_id";
		String sortinMethod = "ASC";
		int pageSize = 10;
		int offset = 10; // la deuxième page

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(columnNameForSorting,
				sortinMethod, offset, pageSize);

		// THEN
		assertNotNull(subcontractors);
		assertFalse(subcontractors.isEmpty());
		assertThat(subcontractors).hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Engie");
	}

	@Test
	public void testFindAllNonArchivedSubcontractors_InvalidCoulmn_ShouldReturnAnEmptyList() {

		// GIVEN
		String columnNameForSorting = "invalid_column";
		String sortinMethod = "ASC";
		int pageSize = 10;
		int offset = 0; // la première page
		
		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(columnNameForSorting, sortinMethod, pageSize, offset);
		
		// THEN
		assertNotNull(subcontractors);
		assertTrue(subcontractors.isEmpty());
	}
	
    @Test
    public void testFindAllSubcontractorsWithStatus_ValidParameters_ShouldReturnNonEmptyList() {
        // GIVEN
        String columnNameForSorting = "s_name";
        String sortingMethod = "ASC";
        int pageSize = 10;
        int offset = 0;
        int statusId = 1;

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsWithStatus(columnNameForSorting, sortingMethod, pageSize, offset, statusId);

        // THEN
        assertNotNull(subcontractors);
        assertFalse(subcontractors.isEmpty());
    }

    // Negative test case with an invalid column
    @Test
    public void testFindAllSubcontractorsWithStatus_InvalidColumn_ShouldReturnEmptyList() {
        // GIVEN
        String columnNameForSorting = "invalid_column";
        String sortingMethod = "ASC";
        int pageSize = 10;
        int offset = 0;
        int statusId = 1; // Assuming a valid statusId

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsWithStatus(columnNameForSorting, sortingMethod, pageSize, offset, statusId);

        // THEN
        assertNotNull(subcontractors);
        assertTrue(subcontractors.isEmpty());
    }

    // Negative test case with an invalid sorting method
    @Test
    public void testFindAllSubcontractorsWithStatus_InvalidSortingMethod_ShouldReturnEmptyList() {
        // GIVEN
        String columnNameForSorting = "s_name";
        String sortingMethod = "InvalidSortingMethod";
        int pageSize = 10;
        int offset = 0;
        int statusId = 1; // Assuming a valid statusId

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsWithStatus(columnNameForSorting, sortingMethod, pageSize, offset, statusId);

        // THEN
        assertNotNull(subcontractors);
        assertTrue(subcontractors.isEmpty());
    }

    // Negative test case with an invalid status ID
    @Test
    public void testFindAllSubcontractorsWithStatus_InvalidStatusId_ShouldReturnEmptyList() {
        // GIVEN
        String columnNameForSorting = "s_name";
        String sortingMethod = "ASC";
        int pageSize = 10;
        int offset = 0;
        int invalidStatusId = -1; // Assuming an invalid statusId

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsWithStatus(columnNameForSorting, sortingMethod, pageSize, offset, invalidStatusId);

        // THEN
        assertNotNull(subcontractors);
        assertTrue(subcontractors.isEmpty());
    }

}
