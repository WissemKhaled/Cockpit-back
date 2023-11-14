package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.SubcontractorNotFoundException;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.mappers.SubcontractorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubcontractorServiceImpl implements SubcontractorService {

	private final SubcontractorDtoMapper dtoMapper;
	private final SubcontractorMapper subcontractorMapper;

	@Override
	public Subcontractor getSubcontractorWithStatus(int sId) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(sId);
		if (subcontractor == null) {
			throw new SubcontractorNotFoundException("le sous-traitant avec l'id: " + sId + " n'existe pas!!");
		}
		return subcontractor;
	}

	@Override
	public int saveSubcontractor(Subcontractor subcontractor) {
		subcontractor.setSCreationDate(LocalDateTime.now());
		int isSubcontractorInserted = subcontractorMapper.insertSubcontractor(subcontractor);
		if (isSubcontractorInserted == 0) {
			return isSubcontractorInserted;
		}
		// remarque qu'on persiste le sous-traitant, on génere l'id automatiquement et
		// comme ça on peut retourner le correct sans prendre en cpnsidération l'id
		// saisi par l'utilisateur (subcontractDto)
		return subcontractor.getSId();
	}

	@Override
	public int updateSubcontractor(Subcontractor subcontractor) {
		subcontractor.setSLastUpdateDate(LocalDateTime.now());
		return subcontractorMapper.updateSubcontractor(subcontractor);
	}

	@Override
	public int archiveSubcontractor(Subcontractor subcontractortoArchive) {
		return subcontractorMapper.archive(subcontractortoArchive);
	}

	// methode qui retourne tous les sousTraitants en DTO et qui prend en parametre
	// pour le tri le nom de la colonne et le type de tri et pour la pagination le
	// nombre déelement a aficcher et la page en question
	@Override
	public List<SubcontractorDto> getAllSubcontractor(String nameColonne, String sorting, int page, int pageSize  ) {
		
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractors(nameColonne, sorting, offset,
				pageSize);
		if (!subContarcList.isEmpty()) {
			for (Subcontractor subcontractor : subContarcList) {
				subcontractorDtosList.add(dtoMapper.subcontractorToDto(subcontractor));
			}
			return subcontractorDtosList;
		} else
			throw new RuntimeException("Il n'y a pas de sous-traitans");
	}

	// ce code permet de retoruner le nombre max de page qu'il y a
	public Integer getNumbersOfPages() {
		return subcontractorMapper.countTotalItems();
	}
	
	// ce code permet de retoruner le nombre max de page qu'il y a
		public Integer countTotalItemsWhitStatus( Integer idStatus) {
			System.err.println(idStatus);
			return subcontractorMapper.countTotalItemsWhitStatus(idStatus);
		}

	@Override
	public List<Status> getAllStatus() {
		return subcontractorMapper.getAllStatus();
	}

	@Override
	public List<SubcontractorDto> getAllSubcontractorWhitStatus(String nameColonne, String sorting, int pageSize,
			int page, int statusId) {
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractorsWhitStatus(nameColonne, sorting,
				offset, pageSize, statusId);

		if (!subContarcList.isEmpty()) {
			for (Subcontractor subcontractor : subContarcList) {
				subcontractorDtosList.add(dtoMapper.subcontractorToDto(subcontractor));
			}
			return subcontractorDtosList;
		} else
			throw new RuntimeException("Il n'y a pas de sous-traitans");
	}

}
