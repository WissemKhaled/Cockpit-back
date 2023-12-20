package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StatusDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.GeneralException;

public interface SubcontractorService {

	List<SubcontractorDto> getAllSubcontractors(String nameColonne, String sorting, int pageSize, int page);

	List<SubcontractorDto> getAllSubcontractorWhitStatus(String nameColonne, String sorting, int pageSize, int page,
			int statusId);

	List<StatusDto> getAllStatus();

	List<SubcontractorDto> getAllSubcontractors();

	int getNumbersOfPages();

	Integer getNumberOfAllSubcontractors();

	Integer countTotalItemWhitStatus(Integer statusId);

	SubcontractorDto getSubcontractorWithStatus(int sId);

	int saveSubcontractor(SubcontractorDto subcontractorDtoToUpdate);

	int updateSubcontractor(SubcontractorDto subcontractorDtoToSave);

	int archiveSubcontractor(SubcontractorDto subcontractortoArchive);

	boolean checkIfSubcontractorExist(int sId);

	int checkIfSubcontractorExistBySName(String sName);

	int checkIfSubcontractorExistBySEmail(String sEmail);

	void handleSubcontractorSave(SubcontractorDto subcontractorDto);

	void handleSubcontractorUpdate(SubcontractorDto subcontractorDto);

	Subcontractor getSubcontractorBySName(String sName);

	/**
	 * Récupère la liste des sous-traitants filtrée par recherche, statut, attribut de recherche, avec prise en compte de la pagination.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param pageNumber       Le numéro de la page à récupérer.
	 * @param pageSize         Le nombre d'éléments par page.
	 * @param statusId      L'ID du statut pour filtrer les sous-traitants (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @return Liste des DTO des sous-traitants filtrés par recherche, statut et attribut de recherche, paginés.
	 * @throws GeneralException Si l'attribut de recherche spécifié n'est pas pris en charge.
	 */
	List<SubcontractorDto> getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms,
			int pageNumber, int pageSize, int statusId, String columnName) throws GeneralException;

	/**
	 * Récupère le nombre de sous-traitants filtré par recherche, statut et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param statusId         L'ID du statut pour filtrer les sous-traitants (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @return Le nombre de sous-traitants selon la recherche, le statut et l'attribut spécifiés.
	 * @throws GeneralException Si l'attribut de recherche spécifié n'est pas pris en charge.
	 */
	Integer getNumberOfSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String columnName) throws GeneralException;

}
