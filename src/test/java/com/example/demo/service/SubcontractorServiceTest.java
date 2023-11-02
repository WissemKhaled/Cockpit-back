package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.SubcontractorNotFoundException;
import com.example.demo.mappers.SubcontractorMapper;

@ExtendWith(MockitoExtension.class)
public class SubcontractorServiceTest {

	@Mock
	private SubcontractorMapper subcontractorMapper;

	@InjectMocks
	private SubcontractorServiceImpl subcontractorService;

	@Test
	public void testGetSubcontractorWithStatus_ExistingId_ShouldReturnDummySubcontractor() {
		// on Mock le comportement de la classe SubcontractorMapper pour qu'elle
		// retourne un dummy Subcontractor
		int existingId = 1;
		Subcontractor dummySubcontractor = new Subcontractor();
		dummySubcontractor.setSId(existingId);
		dummySubcontractor.setSName("Orange");
		dummySubcontractor.setSEmail("Orange@email.fr");
		dummySubcontractor.setStatus(new Status(1, "EN_COURS", "AAAA"));

		// on Mock le comportement pour retourner le dummySubcontractor quand on appelle
		// le mapper
		when(subcontractorMapper.findSubcontractorWithStatusById(anyInt())).thenReturn(dummySubcontractor);

		Subcontractor result = subcontractorService.getSubcontractorWithStatus(1);

		// Assertions pour vérifier que le resultat correspond le dummy Subcontractor
		// attendu
		assertEquals(dummySubcontractor, result);
	}

	@Test
	public void testGetSubcontractorWithStatus_NonExistingId_ShouldReturnErrorMessage() {
		int nonExistingId = Integer.MAX_VALUE;

		// on Mock le comportement de la classe SubcontractorMapper pour qu'elle
		// retourne null pour un ID qui n'existe pas
		when(subcontractorMapper.findSubcontractorWithStatusById(anyInt())).thenReturn(null);

		try {
			subcontractorService.getSubcontractorWithStatus(nonExistingId);
		} catch (SubcontractorNotFoundException e) {
			assertEquals("le sous-traitant avec l'id: " + nonExistingId + " n'existe pas!!", e.getMessage());
		}
	}

	@Test
	public void givenSubcontractorObject_whenSaveSubcontractor_thenReturnOne() {
		// Create a sample Subcontractor object for testing
		Subcontractor subcontractorToSave = new Subcontractor("Sample Name", "sample@example.com");
		subcontractorToSave.setSId(10); // Assuming an ID is set for an existing entry

		// la methode insertSubcontractor retourn 1 si le sous-traitant est persisté
		given(subcontractorMapper.insertSubcontractor(subcontractorToSave)).willReturn(1);

		// La methode retourne l'id du sous-traitant persisté (subcontractorToSave)
		int savedId = subcontractorService.saveSubcontractor(subcontractorToSave);

		assertEquals(subcontractorToSave.getSId(), savedId);
	}

	@Test
	public void givenSubcontractorObject_whenSavingSubcontractorFailed_thenReturnZero() {
		Subcontractor subcontractorToSave = new Subcontractor("Sample Name", "sample@example.com");
		subcontractorToSave.setSId(10);

		// la methode insertSubcontractor retourn 0 si le sous-traitant est n'est pas
		// persisté
		given(subcontractorMapper.insertSubcontractor(subcontractorToSave)).willReturn(0);

		// La methode retourne l'id du sous-traitant persisté (subcontractorToSave)
		int savedId = subcontractorService.saveSubcontractor(subcontractorToSave);

		assertEquals(0, savedId);
	}

	@Test
	public void givenSubcontractorObject_whenUpdatingSubcontractor_thenReturnOne() {
		Subcontractor savedSubcontractor = new Subcontractor(99, "testUpdate", "testUpdate@testUpdate.fr",
				new Status(1, "EN_COURS", "AAAA"));
		given(subcontractorMapper.updateSubcontractor(savedSubcontractor)).willReturn(1);

		// La methode retourne l'id du sous-traitant persisté (subcontractorToSave)
		int isSubcontractorUpdated = subcontractorService.updateSubcontractor(savedSubcontractor);

		assertEquals(1, isSubcontractorUpdated);
	}

	@Test
	public void givenSubcontractorObject_whenUpdatingSubcontractorFailed_thenReturnZero() {
		Subcontractor savedSubcontractor = new Subcontractor(Integer.MAX_VALUE, "testUpdate",
				"testUpdate@testUpdate.fr", new Status(1, "EN_COURS", "AAAA"));

		given(subcontractorMapper.updateSubcontractor(savedSubcontractor)).willReturn(0);

		int isSubcontractorUpdated = subcontractorService.updateSubcontractor(savedSubcontractor);
		assertEquals(0, isSubcontractorUpdated);
	}

}