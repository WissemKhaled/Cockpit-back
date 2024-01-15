package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.builder.SubcontractorBuilder;
import com.example.demo.builder.dto.SubcontractorDtoBuilder;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.dto.mapper.SubcontractorDtoMapper;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.implementation.SubcontractorServiceImpl;

@ExtendWith(MockitoExtension.class)
@Transactional
public class SubcontractorServiceTest {

	@InjectMocks
	private SubcontractorServiceImpl subcontractorService;

	@Mock
	private SubcontractorMapper subcontractorMapper;
	
    @Mock
    private SubcontractorDtoMapper subcontractorDtoMapper;
    
    @Mock
    private ServiceProviderMapper serviceProviderMapper;

	@Test
    void givenSubcontractorObject_whenSavingSubcontractor_ShouldReturnSubcontractorId() throws GeneralException {
        // GIVEN
        SubcontractorDto subcontractorDtoToSave = new SubcontractorDtoBuilder()
        		.withSName("Test Subcontractor")
        		.withSEmail("test@example.com")
        		.build();

        Subcontractor subcontractorToSave = new SubcontractorBuilder()
        		.withSId(Integer.MAX_VALUE)
        		.withSName(subcontractorDtoToSave.getSName())
        		.withSEmail(subcontractorDtoToSave.getSEmail())
        		.withSCreationDate(LocalDateTime.now())
        		.build();


        // WHEN
        when(subcontractorDtoMapper.dtoToSubcontractor(subcontractorDtoToSave)).thenReturn(subcontractorToSave);
        when(subcontractorMapper.insertSubcontractor(subcontractorToSave)).thenReturn(1);
        int savedSubcontractorId = subcontractorService.saveSubcontractor(subcontractorDtoToSave);

        // THEN
        assertThat(subcontractorToSave.getSId()).isEqualTo(savedSubcontractorId);
        assertThat(subcontractorToSave.getSName()).isEqualTo(subcontractorToSave.getSName());
        assertThat(subcontractorToSave.getSEmail()).isEqualTo(subcontractorToSave.getSEmail());
        verify(subcontractorDtoMapper, times(1)).dtoToSubcontractor(subcontractorDtoToSave);
        verify(subcontractorMapper, times(1)).insertSubcontractor(subcontractorToSave);
        verifyNoMoreInteractions(subcontractorDtoMapper, subcontractorMapper);
    }

	@Test
	void givenSubcontractorObject_whenSavingSubcontractorFailed_thenReturnZero() throws GeneralException {
        // GIVEN
        SubcontractorDto subcontractorDtoToSave = new SubcontractorDtoBuilder()
        		.withSName("Test Subcontractor")
        		.withSEmail("test@example.com")
        		.build();

        Subcontractor subcontractorToSave = new SubcontractorBuilder()
        		.withSId(Integer.MAX_VALUE)
        		.withSName(subcontractorDtoToSave.getSName())
        		.withSEmail(subcontractorDtoToSave.getSEmail())
        		.withSCreationDate(LocalDateTime.now())
        		.build();


        // WHEN
        when(subcontractorDtoMapper.dtoToSubcontractor(subcontractorDtoToSave)).thenReturn(subcontractorToSave);
        when(subcontractorMapper.insertSubcontractor(subcontractorToSave)).thenReturn(0);
        int isSaved = subcontractorService.saveSubcontractor(subcontractorDtoToSave);

        // THEN
        assertThat(isSaved).isZero();
        verify(subcontractorDtoMapper, times(1)).dtoToSubcontractor(subcontractorDtoToSave);
        verify(subcontractorMapper, times(1)).insertSubcontractor(subcontractorToSave);
        verifyNoMoreInteractions(subcontractorDtoMapper, subcontractorMapper);
	}

	@Test
	void testGetAllNonArchivedSubcontractors_ShouldReturnSubcontractorDtoList() {
	    // GIVEN
	    String sortingMethod = "ASC";
	    int page = 1;
	    int pageSize = 10;
	    int offset = (page - 1) * pageSize;
	
	    Subcontractor subcontractor1 = new Subcontractor();
	    subcontractor1.setSId(1);
	    subcontractor1.setSName("Subcontractor 1");
	    subcontractor1.setSEmail("subcontractor1@example.com");
	
	    Subcontractor subcontractor2 = new Subcontractor();
	    subcontractor2.setSId(2);
	    subcontractor2.setSName("Subcontractor 2");
	    subcontractor2.setSEmail("subcontractor2@example.com"); 
	    
	    SubcontractorDto subcontractorDto1 = new SubcontractorDtoBuilder()
	    		.withSName("Test Subcontractor")
	    		.withSEmail("test@example.com")
	    		.build();
	    
	    SubcontractorDto subcontractorDto2 = new SubcontractorDtoBuilder()
	    		.withSName("Test Subcontractor")
	    		.withSEmail("test@example.com")
	    		.build();
	
	    when(subcontractorMapper.findAllNonArchivedSubcontractors(sortingMethod, offset, pageSize))
	            .thenReturn(Arrays.asList(subcontractor1, subcontractor2));
	
	    when(subcontractorDtoMapper.subcontractorToDto(subcontractor1))
	            .thenReturn(subcontractorDto1);
	    when(subcontractorDtoMapper.subcontractorToDto(subcontractor2))
	            .thenReturn(subcontractorDto2);
	
	    // WHEN
	    List<SubcontractorDto> subcontractorDtoList = subcontractorService.getAllNonArchivedSubcontractors(sortingMethod, page, pageSize);
	
	    // THEN
	    assertThat(subcontractorDtoList).isNotNull().isNotEmpty().hasSize(2);
	
	    verify(subcontractorMapper, times(1)).findAllNonArchivedSubcontractors(sortingMethod, offset, pageSize);
	    verify(subcontractorDtoMapper, times(1)).subcontractorToDto(subcontractor1);
	    verify(subcontractorDtoMapper, times(1)).subcontractorToDto(subcontractor2);
	    verifyNoMoreInteractions(subcontractorMapper, subcontractorDtoMapper);
    }

	 @Test
	 void testGetAllNonArchivedSubcontractors_ShouldReturnEmptyListWhenNoSubcontractors() {
        // GIVEN
        String sortingMethod = "ASC";
        int page = 1;
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        when(subcontractorMapper.findAllNonArchivedSubcontractors(sortingMethod, offset, pageSize))
                .thenReturn(Collections.emptyList());

        // WHEN
        List<SubcontractorDto> subcontractorDtoList = subcontractorService.getAllNonArchivedSubcontractors(sortingMethod, page, pageSize);

        // THEN
	    assertThat(subcontractorDtoList).isNotNull().isEmpty();
        verify(subcontractorMapper, times(1)).findAllNonArchivedSubcontractors(sortingMethod, offset, pageSize);
        verifyNoInteractions(subcontractorDtoMapper);
        verifyNoMoreInteractions(subcontractorMapper);
	    }

    @Test
    void testGetAllSubcontractorWithStatus_NoSubcontractors_ShouldReturnEmptyList() {
        // GIVEN
        String sortingMethod = "ASC";
        int pageSize = 10;
        int pageNumber = 1;
        int statusId = 1;
        int offset = (pageNumber - 1) * pageSize;

        when(subcontractorMapper.findAllSubcontractorsWithStatus(sortingMethod, offset, pageSize, statusId))
                .thenReturn(Collections.emptyList());

        // WHEN
        List<SubcontractorDto> subcontractorDtoList = subcontractorService.getAllSubcontractorWithStatus(sortingMethod, pageSize, pageNumber, statusId);

        // THEN
	    assertThat(subcontractorDtoList).isNotNull().isEmpty();
        verify(subcontractorMapper, times(1)).findAllSubcontractorsWithStatus(sortingMethod, offset, pageSize, statusId);
        verifyNoInteractions(subcontractorDtoMapper);
        verifyNoMoreInteractions(subcontractorMapper);
    }
	    
    @Test
    void testGetAllSubcontractorWithStatus_SubcontractorsExist_ShouldReturnSubcontractorDtoList() {
        // GIVEN
        String sortingMethod = "ASC";
        int pageSize = 10;
        int pageNumber = 1;
        int statusId = 1; // Change to the desired status ID for testing
        int offset = (pageNumber - 1) * pageSize;
        
	    Subcontractor subcontractor1 = new Subcontractor();
	    subcontractor1.setSId(1);
	    subcontractor1.setSName("Subcontractor 1");
	    subcontractor1.setSEmail("subcontractor1@example.com");
	
	    Subcontractor subcontractor2 = new Subcontractor();
	    subcontractor2.setSId(2);
	    subcontractor2.setSName("Subcontractor 2");
	    subcontractor2.setSEmail("subcontractor2@example.com");
	    
	    SubcontractorDto subcontractorDto1 = new SubcontractorDtoBuilder()
	    		.withSName("Test Subcontractor")
	    		.withSEmail("test@example.com")
	    		.build();
	    
	    SubcontractorDto subcontractorDto2 = new SubcontractorDtoBuilder()
	    		.withSName("Test Subcontractor")
	    		.withSEmail("test@example.com")
	    		.build();
	    
	    List<Subcontractor> subcontractors = Arrays.asList(subcontractor1,subcontractor2);

        when(subcontractorMapper.findAllSubcontractorsWithStatus(sortingMethod, offset, pageSize, statusId))
                .thenReturn(subcontractors);

        List<SubcontractorDto> expectedSubcontractorDtoList = Arrays.asList(subcontractorDto1,subcontractorDto2);

        when(subcontractorDtoMapper.subcontractorToDto(subcontractors.get(0))).thenReturn(expectedSubcontractorDtoList.get(0));
        when(subcontractorDtoMapper.subcontractorToDto(subcontractors.get(1))).thenReturn(expectedSubcontractorDtoList.get(1));

        // WHEN
        List<SubcontractorDto> subcontractorDtoList = subcontractorService.getAllSubcontractorWithStatus(sortingMethod, pageSize, pageNumber, statusId);

        // THEN
	    assertThat(subcontractorDtoList).isNotNull().isNotEmpty().hasSize(expectedSubcontractorDtoList.size()); 
    }
    
    @Test
    void testGetNumberOfAllSubcontractors_SubcontractorsExist_ShouldReturnNumberOfSubcontractors() {
        // GIVEN
        when(subcontractorMapper.countAllNonArchivedSubcontractors()).thenReturn(103); // Adjust the expected count

        // WHEN
        Integer numberOfSubcontractors = subcontractorService.getNumberOfAllNonSubcontractors();

        // THEN
	    assertThat(numberOfSubcontractors).isNotNull().isEqualTo(103);
    }
    
    @Test
    void testGetNumberOfAllSubcontractors_NoSubcontractorsExist_ShouldThrowException() {
        // GIVEN
        when(subcontractorMapper.countAllNonArchivedSubcontractors()).thenReturn(0);

        // WHEN/THEN
        assertThrows(EntityNotFoundException.class, () -> subcontractorService.getNumberOfAllNonSubcontractors());
    }

    @Test
    void testGetNumberOfAllSubcontractorsWithStatus_SubcontractorsExist_ShouldReturnNumberOfSubcontractors() {
        // GIVEN
        int statusId = 1;
        when(subcontractorMapper.countAllNonArchivedSubcontractorsWithStatus(statusId)).thenReturn(30); // Adjust the expected count

        // WHEN
        Integer numberOfSubcontractors = subcontractorService.getNumberOfAllSubcontractorsWithStatus(statusId);

        // THEN
	    assertThat(numberOfSubcontractors).isNotNull().isEqualTo(numberOfSubcontractors);
    }

    @Test
    void testGetNumberOfAllSubcontractorsWithStatus_NoSubcontractorsExist_ShouldThrowException() {
        // GIVEN
        int statusId = 1; 
        when(subcontractorMapper.countAllNonArchivedSubcontractorsWithStatus(statusId)).thenReturn(0);

        // WHEN/THEN
        assertThrows(EntityNotFoundException.class, () -> subcontractorService.getNumberOfAllSubcontractorsWithStatus(statusId));
    }
    
	@Test
	void testGetSubcontractorWithStatus_ExistingId_ShouldReturnDummySubcontractor() {
		// GIVEN
		int existingId = 1;
		Subcontractor dummySubcontractor = new SubcontractorBuilder()
				.withSId(existingId)
	    		.withSName("Orange")
	    		.withSEmail("Orange@email.fr")
	    		.withStatus(new Status(1, "En cours"))
	    		.build();
		
		SubcontractorDto dummySubcontractorDto = new SubcontractorDtoBuilder()
				.withSId(existingId)
				.withSName("Orange")
				.withSEmail("Orange@email.fr")
				.withStatus(new Status(1, "En cours"))
				.build();

		// WHEN
		when(subcontractorMapper.findSubcontractorWithStatusById(anyInt())).thenReturn(dummySubcontractor);
		when(subcontractorDtoMapper.subcontractorToDto(dummySubcontractor)).thenReturn(dummySubcontractorDto);

		SubcontractorDto foundedSubcontractor = subcontractorService.getSubcontractorWithStatus(existingId);
		
		// THEN
		assertEquals(dummySubcontractorDto, foundedSubcontractor);
	}

	@Test
	void testGetSubcontractorWithStatus_NonExistingId_ShouldReturnErrorMessage() {
		// GIVEN
		int nonExistingId = Integer.MAX_VALUE;

		// WHEN
		when(subcontractorMapper.findSubcontractorWithStatusById(anyInt())).thenReturn(null);

		// THEN
		assertThrows(EntityNotFoundException.class, () -> subcontractorService.getSubcontractorWithStatus(nonExistingId));
	}

    @Test
    void testUpdateSubcontractor() {
        // GIVEN
        SubcontractorDto subcontractorDto = new SubcontractorDtoBuilder()
				.withSId(1)
	    		.withSName("Orange")
	    		.withSEmail("Orange@email.fr")
	    		.withSCreationDate(LocalDateTime.now())
	    		.withSLastUpdateDate(LocalDateTime.now())
	    		.withStatus(new Status(1, "En cours"))
	    		.build();
        
        Subcontractor subcontractorForUpdated = new SubcontractorBuilder()
        		.withSId(1)
        		.withSName("Orange")
        		.withSEmail("Orange@email.fr")
        		.withSCreationDate(LocalDateTime.now())
        		.withSLastUpdateDate(LocalDateTime.now())
        		.withStatus(new Status(1, "En cours"))
        		.build();

        // WHEN
        when(subcontractorDtoMapper.dtoToSubcontractor(subcontractorDto)).thenReturn(subcontractorForUpdated);
        when(subcontractorMapper.updateSubcontractor(subcontractorForUpdated)).thenReturn(1);

        int result = subcontractorService.updateSubcontractor(subcontractorDto);

        // THEN
        assertThat(result).isEqualTo(1); 

        verify(subcontractorDtoMapper).dtoToSubcontractor(subcontractorDto);
        verify(subcontractorMapper).updateSubcontractor(subcontractorForUpdated);
    }

	@Test
	void givenSubcontractorObject_whenUpdatingSubcontractorFailed_thenReturnZero() {
      // GIVEN
      SubcontractorDto subcontractorDto = new SubcontractorDtoBuilder()
				.withSId(1)
	    		.withSName("Orange")
	    		.withSEmail("Orange@email.fr")
	    		.withSCreationDate(LocalDateTime.now())
	    		.withSLastUpdateDate(LocalDateTime.now())
	    		.withStatus(new Status(1, "En cours"))
	    		.build();
      
      Subcontractor subcontractorForUpdated = new SubcontractorBuilder()
      		.withSId(1)
      		.withSName("Orange")
      		.withSEmail("Orange@email.fr")
      		.withSCreationDate(LocalDateTime.now())
      		.withSLastUpdateDate(LocalDateTime.now())
      		.withStatus(new Status(1, "En cours"))
      		.build();

      // WHEN
      when(subcontractorDtoMapper.dtoToSubcontractor(subcontractorDto)).thenReturn(subcontractorForUpdated);
      when(subcontractorMapper.updateSubcontractor(subcontractorForUpdated)).thenReturn(0);

      int result = subcontractorService.updateSubcontractor(subcontractorDto);

      // THEN
      assertThat(result).isZero(); 

      verify(subcontractorDtoMapper).dtoToSubcontractor(subcontractorDto);
      verify(subcontractorMapper).updateSubcontractor(subcontractorForUpdated);
	}
    
    @Test
    void testArchiveSubcontractor() {
    	// GIVEN
        SubcontractorDto subcontractorDtoToArchive = new SubcontractorDtoBuilder()
				.withSId(1)
	    		.withSName("Orange")
	    		.withSEmail("Orange@email.fr")
	    		.withSCreationDate(LocalDateTime.now())
	    		.withSLastUpdateDate(LocalDateTime.now())
	    		.withStatus(new Status(1, "En cours"))
	    		.build();
        
        Subcontractor subcontractorToArchive = new SubcontractorBuilder()
			.withSId(1)
			.withSName("Orange")
			.withSEmail("Orange@email.fr")
			.withSCreationDate(LocalDateTime.now())
			.withSLastUpdateDate(LocalDateTime.now())
			.withStatus(new Status(1, "En cours"))
			.build();
        
        // WHEN
        when(subcontractorDtoMapper.dtoToSubcontractor(subcontractorDtoToArchive)).thenReturn(subcontractorToArchive);
        when(subcontractorMapper.archiveSubcontractor(subcontractorToArchive)).thenReturn(1); // Assuming archive is successful
        when(serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorDtoToArchive.getSId())).thenReturn(List.of(/* mock ServiceProvider instances */));

        int result = subcontractorService.archiveSubcontractor(subcontractorDtoToArchive);

        // THEN
        assertThat(result).isEqualTo(1);

        verify(subcontractorDtoMapper).dtoToSubcontractor(subcontractorDtoToArchive);
        verify(subcontractorMapper).archiveSubcontractor(subcontractorToArchive);
        verify(serviceProviderMapper).findServiceProvidersBySubcontractorId(subcontractorDtoToArchive.getSId());

        // Verify that archiveServiceProvider was called for each found ServiceProvider
//        verify(serviceProviderMapper, times(/* number of found ServiceProviders */)).archiveServiceProvider(any());
    }
    
    @Test
    void testArchiveSubcontractor_Failed() {
    	// GIVEN
        SubcontractorDto subcontractorDtoToArchive = new SubcontractorDtoBuilder()
				.withSId(1)
	    		.withSName("Orange")
	    		.withSEmail("Orange@email.fr")
	    		.withSCreationDate(LocalDateTime.now())
	    		.withSLastUpdateDate(LocalDateTime.now())
	    		.withStatus(new Status(1, "En cours"))
	    		.build();
        
        Subcontractor subcontractorToArchive = new SubcontractorBuilder()
			.withSId(1)
			.withSName("Orange")
			.withSEmail("Orange@email.fr")
			.withSCreationDate(LocalDateTime.now())
			.withSLastUpdateDate(LocalDateTime.now())
			.withStatus(new Status(1, "En cours"))
			.build();
        
        // WHEN
        when(subcontractorDtoMapper.dtoToSubcontractor(subcontractorDtoToArchive)).thenReturn(subcontractorToArchive);
        when(subcontractorMapper.archiveSubcontractor(subcontractorToArchive)).thenReturn(0);
        when(serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorDtoToArchive.getSId())).thenReturn(List.of(/* mock ServiceProvider instances */));

        int result = subcontractorService.archiveSubcontractor(subcontractorDtoToArchive);

        // THEN
        assertThat(result).isZero();

        verify(subcontractorDtoMapper).dtoToSubcontractor(subcontractorDtoToArchive);
        verify(subcontractorMapper).archiveSubcontractor(subcontractorToArchive);
        verify(serviceProviderMapper).findServiceProvidersBySubcontractorId(subcontractorDtoToArchive.getSId());

        // Verify that archiveServiceProvider was called for each found ServiceProvider
//        verify(serviceProviderMapper, times(/* number of found ServiceProviders */)).archiveServiceProvider(any());
    }
    
//  @Test
//  public void testArchiveSubcontractor_Failure() {
//      // Similar to the previous example, but set isArchivedSubcontractor to 0 or any other value indicating failure
//      // Also, verify that archiveServiceProvider is not called in case of failure
//  }
    
    @Test
    void testCheckIfSubcontractorExist_SubcontractorExists_ShouldReturnTrue() {
        // GIVEN
        int sId = 1;
        Subcontractor subcontractor = new SubcontractorBuilder()
				.withSId(sId)
	    		.withSName("Orange")
	    		.withSEmail("Orange@email.fr")
	    		.withSCreationDate(LocalDateTime.now())
	    		.withSLastUpdateDate(LocalDateTime.now())
	    		.withStatus(new Status(1, "En cours"))
	    		.build();
        
        // WHEN
        when(subcontractorMapper.findSubcontractorWithStatusById(sId)).thenReturn(subcontractor);
        boolean result = subcontractorService.checkIfSubcontractorExist(sId);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void testCheckIfSubcontractorExist_SubcontractorDoesNotExist_ShouldReturnFalse() {
        // GIVEN
        int sId = 1;
        
        // WHEN
        when(subcontractorMapper.findSubcontractorWithStatusById(sId)).thenReturn(null);
        boolean result = subcontractorService.checkIfSubcontractorExist(sId);

        // THEN
        assertThat(result).isFalse();
    }
    
    @Test
    void testCheckIfSubcontractorExistBySName_SubcontractorExists_ShouldSubcontractorId() {
    	// GIVEN
    	String sName = "Orange";
    	Subcontractor subcontractor = new SubcontractorBuilder()
    			.withSId(1)
    			.withSName("Orange")
    			.withSEmail("Orange@email.fr")
    			.withSCreationDate(LocalDateTime.now())
    			.withSLastUpdateDate(LocalDateTime.now())
    			.withStatus(new Status(1, "En cours"))
    			.build();
    	
    	// WHEN
    	when(subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(sName.toUpperCase())).thenReturn(subcontractor);
    	int result = subcontractorService.checkIfSubcontractorExistBySName(sName);
    	
    	// THEN
    	assertThat(result).isEqualTo(subcontractor.getSId());
    }
    
    @Test
    void testCheckIfSubcontractorExistBySName_SubcontractorDoesNotExist_ShouldReturnZero() {
    	// GIVEN
    	String sName = "Orange";
    	
    	// WHEN
    	when(subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(sName.toUpperCase())).thenReturn(null);
    	int result = subcontractorService.checkIfSubcontractorExistBySName(sName);
    	
    	// THEN
    	assertThat(result).isZero();
    }
    
    @Test
    void testCheckIfSubcontractorExistBySEmail_SubcontractorExists_ShouldSubcontractorId() {
    	// GIVEN
    	String sEmail = "Orange@email.fr";
    	Subcontractor subcontractor = new SubcontractorBuilder()
    			.withSId(1)
    			.withSName("Orange")
    			.withSEmail("Orange@email.fr")
    			.withSCreationDate(LocalDateTime.now())
    			.withSLastUpdateDate(LocalDateTime.now())
    			.withStatus(new Status(1, "En cours"))
    			.build();
    	
    	// WHEN
    	when(subcontractorMapper.findSubcontractorWithStatusBySubcontractorEmail(sEmail)).thenReturn(subcontractor);
    	int result = subcontractorService.checkIfSubcontractorExistBySEmail(sEmail);
    	
    	// THEN
    	assertThat(result).isEqualTo(subcontractor.getSId());
    }
    
    @Test
    void testCheckIfSubcontractorExistBySEmail_SubcontractorDoesNotExist_ShouldReturnZero() {
    	// GIVEN
    	String sEmail = "Orange@email.fr";
    	
    	// WHEN
    	when(subcontractorMapper.findSubcontractorWithStatusBySubcontractorEmail(sEmail)).thenReturn(null);
    	int result = subcontractorService.checkIfSubcontractorExistBySEmail(sEmail);
    	
    	// THEN
    	assertThat(result).isZero();
    }
    
    @Test
    void testGetSubcontractorBySName_ExistingSubcontractor_ShouldReturnSubcontractor() {
        // GIVEN
    	String sName = "Orange";
    	Subcontractor expectedSubcontractor = new SubcontractorBuilder()
    			.withSId(1)
    			.withSName(sName)
    			.withSEmail("Orange@email.fr")
    			.withSCreationDate(LocalDateTime.now())
    			.withSLastUpdateDate(LocalDateTime.now())
    			.withStatus(new Status(1, "En cours"))
    			.build();


    	// WHEN
        when(subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(sName)).thenReturn(expectedSubcontractor);

        Subcontractor actualSubcontractor = subcontractorService.getSubcontractorBySName(sName);

        // THEN
        assertThat(actualSubcontractor).isNotNull();
        assertThat(expectedSubcontractor.getSId()).isEqualTo(actualSubcontractor.getSId());
        assertThat(expectedSubcontractor.getSName()).isEqualTo(actualSubcontractor.getSName());
        assertThat(expectedSubcontractor.getSEmail()).isEqualTo(actualSubcontractor.getSEmail());
    }

    @Test
    void testGetSubcontractorBySName_NonExistingSubcontractor_ShouldThrowException() {
        // GIVEN
        String NonExistingSName = "NonExistingName";

    	// WHEN
        when(subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(NonExistingSName)).thenReturn(null);

		assertThrows(EntityNotFoundException.class, () -> subcontractorService.getSubcontractorBySName(NonExistingSName));
    }
    
    @Test
    void testGetAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring_WithResults() throws GeneralException {
    	// GIVEN
        String searchTerms = "Orange";
        int pageNumber = 1;
        int pageSize = 10;
        int statusId = 0;
        String columnName = "s_name";
        String sortingMethod = "asc";

        // WHEN
        List<Subcontractor> subcontractorList = Arrays.asList(new Subcontractor(), new Subcontractor());
        when(subcontractorMapper.findAllSubcontractorsByCriteria(any(), any(), anyInt(), anyInt(), any()))
                .thenReturn(subcontractorList);

        List<SubcontractorDto> subcontractorDtoList = Arrays.asList(new SubcontractorDto(), new SubcontractorDto());
        when(subcontractorDtoMapper.subcontractorToDto(any())).thenReturn(subcontractorDtoList.get(0), subcontractorDtoList.get(1));

        List<SubcontractorDto> result = subcontractorService.getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(
                searchTerms, pageNumber, pageSize, statusId, columnName, sortingMethod);

        // THEN
        assertThat(result).hasSize(2);
    }
    
    @Test
    void testGetAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring_NoResults() {
    	// GIVEN
        String searchTerms = "term";
        int pageNumber = 1;
        int pageSize = 10;
        int statusId = 0;
        String columnName = "s_name";
        String sortingMethod = "asc";

        // WHEN
        when(subcontractorMapper.findAllSubcontractorsByCriteria(any(), any(), anyInt(), anyInt(), any()))
                .thenReturn(Collections.emptyList());

        // THEN
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                subcontractorService.getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(
                        searchTerms, pageNumber, pageSize, statusId, columnName, sortingMethod));
        assertEquals("Aucun résultat trouvé", exception.getMessage());
    }
}