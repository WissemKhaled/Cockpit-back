package com.example.demo.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		Subcontractor existingSubcontractor = new SubcontractorBuilder()
				.withSId(1).withSName("Orange")
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
				.withStatus(new Status(1)).build();

		// WHEN
		int isNotArchived = subcontractorMapper.archiveSubcontractor(nonExistingSubcontractor);

		// THEN
		assertEquals(0, isNotArchived);
	}

	@Test
	void testCountAllNonArchivedSubcontractors_ShouldReturnExpectedCount() {

		// GIVEN
		int expectedCount = 101;

		// WHEN
		int count = subcontractorMapper.countAllNonArchivedSubcontractors();

		// THEN
		assertEquals(expectedCount, count); // Replace expectedCount with the expected result
	}

	@Test
	void testCountAllNonArchivedSubcontractorsWithStatus_StatusSelectedWithIdEqualsOne_ShouldReturnThirty() {

		// GIVEN
		int expectedCount = 30;

		// WHEN
		int count = subcontractorMapper.countAllNonArchivedSubcontractorsWithStatus(1);

		// THEN
		assertEquals(expectedCount, count);
	}

	@Test
	void testFindAllNonArchivedSubcontractors_10ItemsSortedByStatusColumnAndNameInAscendingOrder_ShouldReturn10Subcontractors() {

		// GIVEN
		String sortinMethod = "ASC";

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(sortinMethod, 0, 10);

		// THEN
        assertThat(subcontractors).isNotNull().isNotEmpty().hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Alstom");
	}

	@Test
	void testFindAllNonArchivedSubcontractors_10ItemsSortedByStatusColumnInDescendingOrder_ShouldReturn10Subcontractors() {

		// GIVEN
		String sortinMethod = "DESC";

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(sortinMethod, 0, 10);

		// THEN
        assertThat(subcontractors).isNotNull().isNotNull().isNotEmpty().hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("VirtuosoSoft Solutions");
	}


	@Test
	void testFindAllNonArchivedSubcontractors_20ItemsSortedByStatusColumnInAscendingOrder_ShouldReturn20Subcontractors() {

		// GIVEN
		String sortinMethod = "ASC";
		int pageSize = 20;
		int offset = 0; // la première page

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(sortinMethod, offset, pageSize);

		// THEN
        assertThat(subcontractors).isNotNull().isNotEmpty().hasSize(20);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Alstom");
	}

	@Test
	void testFindAllNonArchivedSubcontractors_10ItemsSortedByStatusColumnInAscendingOrder_ShouldReturn10SubcontractorInSecondPage() {

		// GIVEN
		String sortinMethod = "ASC";
		int pageSize = 10;
		int offset = 10; // la deuxième page

		// WHEN
		List<Subcontractor> subcontractors = subcontractorMapper.findAllNonArchivedSubcontractors(sortinMethod, offset, pageSize);

		// THEN
        assertThat(subcontractors).isNotNull().isNotEmpty().hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Engie");
	}

    @Test
    void testFindAllSubcontractorsWithStatus_ValidParameters_ShouldReturnNonEmptyList() {
        
    	// GIVEN
        String sortingMethod = "ASC";
        int pageSize = 10;
        int offset = 0;
        int statusId = 1;

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsWithStatus(sortingMethod, offset, pageSize, statusId);

        // THEN
        assertThat(subcontractors).isNotNull().isNotEmpty().hasSize(10);
		assertThat(subcontractors.get(0).getSName()).isEqualTo("Alstom");
    }

    @Test
    void testFindAllSubcontractorsWithStatus_InvalidStatusId_ShouldReturnEmptyList() {
        
    	// GIVEN
        String sortingMethod = "ASC";
        int pageSize = 10;
        int offset = 0;
        int invalidStatusId = -1; // Assuming an invalid statusId

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsWithStatus(sortingMethod, offset, pageSize, invalidStatusId);

        // THEN
        assertThat(subcontractors).isNotNull().isEmpty();
    }
    
    @Test
    void testFindSubcontractorWithStatusById_ValidId_ShouldReturnSubcontractor() {
        
    	// GIVEN
        int validId = 1; 

        // WHEN
        Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(validId);

        // THEN
        assertThat(subcontractor).isNotNull();
        assertThat(subcontractor.getStatus()).isNotNull();
        assertThat(subcontractor.getSId()).isEqualTo(validId);
        assertThat(subcontractor.getSName()).isEqualTo("Orange");
        assertThat(subcontractor.getSEmail()).isEqualTo("Orange@email.fr");
        assertThat(subcontractor.getStatus().getStId()).isEqualTo(1);
    }

    @Test
    void testFindSubcontractorWithStatusById_InvalidId_ShouldReturnNull() {
        
    	// GIVEN
        int invalidId = -1; 

        // WHEN
        Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(invalidId);

        // THEN
        assertThat(subcontractor).isNull();
    }
    
    @Test
    void testFindSubcontractorWithStatusBySubcontractorName_ValidId_ShouldReturnSubcontractor() {
        
    	// GIVEN
        int validId = 1; 

        // WHEN
        Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySubcontractorName("Orange");

        // THEN
        assertThat(subcontractor).isNotNull();
        assertThat(subcontractor.getStatus()).isNotNull();
        assertThat(subcontractor.getSId()).isEqualTo(validId);
        assertThat(subcontractor.getSName()).isEqualTo("Orange");
        assertThat(subcontractor.getSEmail()).isEqualTo("Orange@email.fr");
        assertThat(subcontractor.getStatus().getStId()).isEqualTo(1);
    }

    @Test
    void testFindSubcontractorWithStatusBySubcontractorName_InvalidSubcontractorName_ShouldReturnNull() {
        
    	// GIVEN
        String nonExistingSubcontractorName = "Non existing subcontractor"; 

        // WHEN
        Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(nonExistingSubcontractorName);

        // THEN
        assertThat(subcontractor).isNull();
    }
    
    @Test
    void testFfindSubcontractorWithStatusBySubcontractorEmail_ValidId_ShouldReturnSubcontractor() {
        
    	// GIVEN
        int validId = 1; 

        // WHEN
        Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySubcontractorEmail("Orange@email.fr");

        // THEN
        assertThat(subcontractor).isNotNull();
        assertThat(subcontractor.getStatus()).isNotNull();
        assertThat(subcontractor.getSId()).isEqualTo(validId);
        assertThat(subcontractor.getSName()).isEqualTo("Orange");
        assertThat(subcontractor.getSEmail()).isEqualTo("Orange@email.fr");
        assertThat(subcontractor.getStatus().getStId()).isEqualTo(1);
    }

    @Test
    void testFindSubcontractorWithStatusBySubcontractorEmail_InvalidSubcontractorName_ShouldReturnNull() {
        
    	// GIVEN
        String nonExistingSubcontractorEmail = "Non existing subcontractor email"; 

        // WHEN
        Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySubcontractorEmail(nonExistingSubcontractorEmail);

        // THEN
        assertThat(subcontractor).isNull();
    }
    
    @Test
    void testInsertSubcontractor_ValidSubcontractor_ShouldInsertAndReturnId() {
        
    	// GIVEN
    	String dateString = "2024-01-11T16:14:26.537365700";
    	LocalDateTime dateTime = dateFormatter(dateString);

        Subcontractor subcontractor = new SubcontractorBuilder()
        		.withSName("Test Subcontractor")
        		.withSEmail("test@example.com")
        		.withSCreationDate(dateTime)
        		.withSLastUpdateDate(dateTime)
        		.withStatus(new Status(1,"En cours"))
        		.build();
        
        // WHEN
        int result = subcontractorMapper.insertSubcontractor(subcontractor);

        // THEN
        assertThat(result).isPositive();
        assertThat(subcontractor.getSId()).isPositive(); // on attend que l'id soit inséré dans l'objet Subcontractor
    }
    
    @Test
    void testInsertSubcontractor_NullSubcontractor_ShouldThrowException() {
        
    	// GIVEN
        Subcontractor subcontractor = null;

        // WHEN / THEN
        assertThrows(Exception.class, () -> subcontractorMapper.insertSubcontractor(subcontractor));
    }
    
    @Test
    void testUpdateSubcontractor_ValidSubcontractor_ShouldReturnSuccessIndicator() {
        
    	// GIVEN
    	String dateString = "2024-01-11T16:14:26.537365700";
    	LocalDateTime dateTime = dateFormatter(dateString);
        
        Subcontractor subcontractor = new SubcontractorBuilder()
        		.withSId(1)
        		.withSName("Updated Name")
        		.withSEmail("updated@example.com")
        		.withSCreationDate(dateTime)
        		.withSLastUpdateDate(dateTime)
        		.withStatus(new Status(2,"En validation"))
        		.build();

        // WHEN
        int result = subcontractorMapper.updateSubcontractor(subcontractor);

        // THEN
        assertThat(result).isEqualTo(1); // on attend un indice de succes (1)
    }
    
    @Test
    void testUpdateSubcontractor_InvalidId_ShouldReturnFailureIndicator() {
        
    	// GIVEN
    	String dateString = "2024-01-11T16:14:26.537365700";
    	LocalDateTime dateTime = dateFormatter(dateString);
    	
        Subcontractor subcontractor = new SubcontractorBuilder()
        		.withSId(-1)
        		.withSName("Updated Name")
        		.withSEmail("updated@example.com")
        		.withSCreationDate(dateTime)
        		.withSLastUpdateDate(dateTime)
        		.withStatus(new Status(2,"En validation"))
        		.build();

        // WHEN
        int result = subcontractorMapper.updateSubcontractor(subcontractor);

        // THEN
        assertThat(result).isZero(); // on attend un indice d'échec (0)
    }
    
    @Test
    void testArchiveSubcontractor_ValidSubcontractor_ShouldReturnSuccessIndicator() {
       
    	// GIVEN
        Subcontractor subcontractorToArchive = new SubcontractorBuilder()
        		.withSId(1)
        		.build();

        // WHEN
        int result = subcontractorMapper.archiveSubcontractor(subcontractorToArchive);

        // THEN
        assertThat(result).isEqualTo(1); // on attend un indice de succes (1)
    }

    @Test
    void testArchiveSubcontractor_InvalidId_ShouldReturnFailureIndicator() {
       
    	// GIVEN
        Subcontractor subcontractorToArchive = new SubcontractorBuilder()
        		.withSId(-1) 
        		.build();

        // WHEN
        int result = subcontractorMapper.archiveSubcontractor(subcontractorToArchive);

        // THEN
        assertThat(result).isZero(); // on attend un indice d'échec (0)
    }
    
    @Test
    void testFindAllSubcontractorsByCriteria_ValidCriteria_ShouldReturnNonEmptyList() {
        // GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";
        int pageSize = 10;
        int offset = 0;
        String sortingMethod = "ASC";

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsByCriteria(columnName, searchTerms, offset, pageSize, sortingMethod);

        // THEN
        assertThat(subcontractors).isNotNull().isNotEmpty();
        assertThat(subcontractors.get(0).getSName()).isSubstringOf("Orange");
    }

    @Test
    void testFindAllSubcontractorsByCriteria_InvalidColumn_ShouldReturnEmptyList() {
        
    	// GIVEN
        String columnName = "invalid_column";
        String searchTerms = "searchTerm";
        int pageSize = 10;
        int offset = 0;
        String sortingMethod = "ASC";

        // WHEN / THEN
        assertThrows(Exception.class, () -> subcontractorMapper.findAllSubcontractorsByCriteria(columnName, searchTerms, offset, pageSize,sortingMethod));
    }

    @Test
    void testFindAllSubcontractorsByCriteria_InvalidSortingMethod_ShouldReturnEmptyList() {
        
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";
        int pageSize = 10;
        int offset = 0;
        String invalidSortingMethod = "InvalidSortingMethod";

        // WHEN / THEN
        assertThrows(Exception.class, () -> subcontractorMapper.findAllSubcontractorsByCriteria(columnName, searchTerms, offset, pageSize,invalidSortingMethod));
    }
    
    @Test
    void testFindAllSubcontractorsByCriteria_NonExistingSubcontractorName_ShouldReturnNonEmptyList() {
        
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "NonExistingSubcontractorName";
        int pageSize = 10;
        int offset = 0;
        String sortingMethod = "ASC";

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsByCriteria(columnName, searchTerms, offset, pageSize, sortingMethod);

        // THEN
        assertThat(subcontractors).isNotNull().isEmpty();;
    }

    @Test
    void testFindAllSubcontractorsByCriteriaAndFiltredByStatus_ValidCriteria_ShouldReturnNonEmptyList() {
       
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";
        int pageSize = 10;
        int offset = 0;
        String sortingMethod = "ASC";
        int statusId = 1;

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsByCriteriaAndFiltredByStatus(columnName, searchTerms, offset, pageSize, sortingMethod, statusId);

        // THEN
      assertThat(subcontractors).isNotNull().isNotEmpty();
      assertThat(subcontractors.get(0).getSName()).isSubstringOf("Orange");
      assertThat(subcontractors.get(0).getStatus().getStId()).isEqualTo(statusId);
    }
    
    @Test
    void testFindAllSubcontractorsByCriteriaAndFiltredByStatus_InvalidColumn_ShouldReturnNonEmptyList() {
        
    	// GIVEN
        String columnName = "invalid_column";
        String searchTerms = "Orang";
        int pageSize = 10;
        int offset = 0;
        String sortingMethod = "ASC";
        int statusId = 1;

        // WHEN / THEN
        assertThrows(Exception.class, () -> subcontractorMapper.findAllSubcontractorsByCriteriaAndFiltredByStatus(columnName, searchTerms, offset, pageSize,sortingMethod,statusId));
    }
    
    @Test
    void testFindAllSubcontractorsByCriteriaAndFiltredByStatus_InvalidSortingMethod_ShouldReturnNonEmptyList() {
        
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";
        int pageSize = 10;
        int offset = 0;
        String invalidSortingMethod = "InvalidSortingMethod";
        int statusId = 1;

      // WHEN / THEN
      assertThrows(Exception.class, () -> subcontractorMapper.findAllSubcontractorsByCriteriaAndFiltredByStatus(columnName, searchTerms, offset, pageSize,invalidSortingMethod,statusId));
    }
    
    @Test
    void testFindAllSubcontractorsByCriteriaAndFiltredByStatus_NonExistingSubcontractorName_ShouldReturnNonEmptyList() {
        
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "NonExistingSubcontractorName";
        int pageSize = 10;
        int offset = 0;
        String sortingMethod = "ASC";
        int statusId = 1;

        // WHEN
        List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractorsByCriteriaAndFiltredByStatus(columnName, searchTerms, offset, pageSize, sortingMethod, statusId);

        // THEN
        assertThat(subcontractors).isNotNull().isEmpty();;
    }
    
    @Test
    void testCountAllSubcontractorsByCriteria_ValidCriteria_ShouldReturnNonNegativeCount() {
        
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";

        // WHEN
        Integer count = subcontractorMapper.countAllSubcontractorsByCriteria(columnName, searchTerms);

        // THEN
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testCountAllSubcontractorsByCriteria_InvalidColumn_ShouldReturnZeroCount() {
        
    	// GIVEN
        String invalidColumnName = "invalid_column";
        String searchTerms = "Orang";

        // WHEN / THEN
        assertThrows(Exception.class, () -> subcontractorMapper.countAllSubcontractorsByCriteria(invalidColumnName, searchTerms));
    }
    
    @Test
    void testCountAllSubcontractorsByCriteriaAndFiltredByStatus_ValidCriteria_ShouldReturnNonNegativeCount() {
       
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";
        int statusId = 1;

        // WHEN
        Integer count = subcontractorMapper.countAllSubcontractorsByCriteriaAndFiltredByStatus(columnName, searchTerms, statusId);

        // THEN
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testCountAllSubcontractorsByCriteriaAndFiltredByStatus_InvalidStatusId_ShouldReturnZeroCount() {
        
    	// GIVEN
        String columnName = "s.s_name";
        String searchTerms = "Orang";
        int invalidStatusId = -1; // Assuming an invalid status ID

        // WHEN
        Integer count = subcontractorMapper.countAllSubcontractorsByCriteriaAndFiltredByStatus(columnName, searchTerms, invalidStatusId);

        // WHEN
        assertThat(count).isNotNull().isZero();
    }

    private LocalDateTime dateFormatter(String dateString) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    	LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
    	return dateTime;
	}
    
}
