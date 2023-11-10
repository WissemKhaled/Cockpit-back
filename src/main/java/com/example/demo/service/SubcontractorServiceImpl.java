package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controller.exception.SubcontractorNotFoundException;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.mappers.SubcontractorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubcontractorServiceImpl implements SubcontractorService {

	private final SubcontractorDtoMapper dtoMapper;
	private final SubcontractorMapper subcontractorMapper;

	@Override
	public int saveSubcontractor(Subcontractor subcontractor) {
		int isSubcontractorInserted = subcontractorMapper.insertSubcontractor(subcontractor);
		if (isSubcontractorInserted == 0) {
			return isSubcontractorInserted;
		}
		// remarque qu'on persiste le sous-traitant, on génere l'id
		// automatiquement et comme ça on peut retourner le correct sans
		// prendre en cpnsidération l'id saisi par l'utilisateur
		// (subcontractDto)
		return subcontractor.getSId();
	}

	@Override
	public Subcontractor getSubcontractorWithStatus(int sId) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(sId);
		if (subcontractor == null) {
			throw new SubcontractorNotFoundException("le sous-traitant avec l'id: " + sId + " n'existe pas!!");
		}
		return subcontractor;
	}

	@Override
	public int updateSubcontractor(Subcontractor subcontractor) {
		int isSubcontractorUpdated = subcontractorMapper.updateSubcontractor(subcontractor);
		return isSubcontractorUpdated;
	}

	// debut hamza : methode qui retourne tous les sousTraitants en DTO et qui prend
	// en parametre
	// pour le tri le nom de la colonne et le type de tri
	// et pour la pagination le nombre déelement a aficcher et la page en question
	@Override
	public List<SubcontractorDto> getAllSubcontractor(String nameColonne, String sorting, int page, int pageSize) {

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
			throw new RuntimeException("Il n'y a pas de sousTraitans");

	}
	// fin

	// debut hamza : ce code permet de retoruner le nombre max de page qu'il y a
	public int getNumbersOfPages() {
		int totalItems = subcontractorMapper.countTotalItems();
		//int nbPages = (int) Math.ceil((double) totalItems / pageSize);

		return totalItems;
	}

	@Override
	public List<Status> getAllStatus() {
		
		List<Status> listStatus = subcontractorMapper.getAllStatus();
		return listStatus;
	}
	

	@Override
	public List<SubcontractorDto> getAllSubcontractorWhitStatus(String nameColonne, String sorting, int pageSize, int page, int statusId) {
		
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractorsWhitStatus(nameColonne, sorting, offset ,pageSize, statusId);

		
		for (Subcontractor subcontractor : subContarcList) {
			System.err.println(subcontractor.toString());
			
		}
		
		if (!subContarcList.isEmpty()) {

			for (Subcontractor subcontractor : subContarcList) {

				subcontractorDtosList.add(dtoMapper.subcontractorToDto(subcontractor));
			}

			return subcontractorDtosList;

		} else
			throw new RuntimeException("Il n'y a pas de sousTraitans");
	}
	// fin

}
