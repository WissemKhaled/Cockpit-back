package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StatusDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.GeneralException;

public interface SubcontractorService {

	/**
	 * Récupère la liste paginée et triée des sous-traitants en fonction des paramètres spécifiés et du statut.
	 *
	 * @param nameColonne La colonne à utiliser pour le tri (par défaut : "s_fk_status_id").
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageSize    Le nombre d'éléments par page (par défaut : 10).
	 * @param page        Le numéro de la page à récupérer (par défaut : 1).
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.
	 * @return Liste des DTO des sous-traitants paginée et triée avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitant n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	List<StatusDto> getAllStatus();
	
	
	/**
	 * Récupère la liste paginée et triée des sous-traitants non archivés en fonction des paramètres spécifiés.
	 *
	 * @param nameColonne La colonne à utiliser pour le tri (par défaut : "s_fk_status_id").
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param page        Le numéro de la page à récupérer (par défaut : 1).
	 * @param pageSize    Le nombre d'éléments par page (par défaut : 10).
	 * @return Liste des DTO des sous-traitants non archivés paginée et triée avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitant n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	List<SubcontractorDto> getAllNonArchivedSubcontractors(String nameColonne, String sorting, int pageSize, int page);

	
	/**
	 * Récupère la liste paginée et triée des sous-traitants en fonction des paramètres spécifiés et du statut.
	 *
	 * @param nameColonne La colonne à utiliser pour le tri (par défaut : "s_fk_status_id").
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageSize    Le nombre d'éléments par page (par défaut : 10).
	 * @param page        Le numéro de la page à récupérer (par défaut : 1).
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.
	 * @return Liste des DTO des sous-traitants paginée et triée avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitant n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	List<SubcontractorDto> getAllSubcontractorWithStatus(String nameColonne, String sorting, int pageSize, int page,
			int statusId);

	
	/**
	 * Récupère le nombre total de sous-traitants enregistrés.
	 *
	 * @return Le nombre total de sous-traitants.
	 * @throws EntityNotFoundException Si aucun sous-traitant n'est trouvé.
	 */
	Integer getNumberOfAllSubcontractors();

	
	/**
	 * Compte le nombre total de sous-traitants avec un statut spécifique.
	 *
	 * @param statusId L'ID du statut pour filtrer les sous-traitants.
	 * @return Le nombre total de sous-traitants avec le statut spécifié.
	 * @throws EntityNotFoundException Si aucun sous-traitant n'est trouvé.
	 */
	Integer getNumberOfAllSubcontractorsWithStatus(Integer statusId);

	
	/**
	 * Récupère un sous-traitant avec son statut à partir de l'ID.
	 *
	 * @param sId L'ID du sous-traitant.
	 * @return Le DTO du sous-traitant avec son statut.
	 * @throws EntityNotFoundException Si le sous-traitant n'est pas trouvé.
	 */
	SubcontractorDto getSubcontractorWithStatus(int sId);

	
	/**
	 * Enregistre un nouveau sous-traitant.
	 *
	 * @param subcontractorDto Le DTO du sous-traitant à enregistrer.
	 * @return L'ID du sous-traitant enregistré ou -1 en cas d'échec.
	 */
	int saveSubcontractor(SubcontractorDto subcontractorDtoToUpdate) throws GeneralException;

	
	/**
	 * Met à jour les informations d'un sous-traitant.
	 *
	 * @param subcontractorDto Le DTO du sous-traitant à mettre à jour.
	 * @return Un indicateur de succès (par exemple, 1 pour succès, 0 pour échec).
	 */
	int updateSubcontractor(SubcontractorDto subcontractorDtoToSave);

	
	/**
	 * Archive un sous-traitant et archive également ses prestataires de services associés.
	 *
	 * @param subcontractorDtoToArchive Le DTO du sous-traitant à archiver.
	 * @return Un indicateur de succès (par exemple, 1 pour succès, 0 pour échec).
	 */
	int archiveSubcontractor(SubcontractorDto subcontractortoArchive);

	
	/**
	 * Vérifie si un sous-traitant existe en fonction de son ID.
	 *
	 * @param sId L'ID du sous-traitant à vérifier.
	 * @return true si le sous-traitant existe, sinon false.
	 */
	boolean checkIfSubcontractorExist(int sId);

	
	/**
	 * Vérifie si un sous-traitant existe en fonction de son nom.
	 *
	 * @param sName Le nom du sous-traitant à vérifier.
	 * @return L'ID du sous-traitant s'il existe, sinon null.
	 */
	int checkIfSubcontractorExistBySName(String sName);

	
	/**
	 * Vérifie si un sous-traitant existe en fonction de son email.
	 *
	 * @param sEmail L'email du sous-traitant à vérifier.
	 * @return L'ID du sous-traitant s'il existe, sinon null.
	 */
	int checkIfSubcontractorExistBySEmail(String sEmail);

	
	/**
	 * Gère la sauvegarde d'un sous-traitant en vérifiant la duplication de données.
	 *
	 * @param subcontractorDto Les données du sous-traitant à sauvegarder.
	 * @throws EntityDuplicateDataException Si le nom ou l'e-mail du sous-traitant existe déjà dans la base de données.
	 */
	void handleSubcontractorSave(SubcontractorDto subcontractorDto);

	
	/**
	 * Gère la mise à jour d'un sous-traitant en vérifiant la duplication de données.
	 *
	 * @param subcontractorDto Les données mises à jour du sous-traitant.
	 * @throws EntityDuplicateDataException Si le nom ou l'e-mail mis à jour existe déjà pour un autre sous-traitant.
	 */
	void handleSubcontractorUpdate(SubcontractorDto subcontractorDto);

	
	/**
	 * Récupère un sous-traitant en fonction de son nom.
	 *
	 * @param sName Le nom du sous-traitant à récupérer.
	 * @return Le sous-traitant trouvé.
	 * @throws EntityNotFoundException Si aucun sous-traitant n'est trouvé avec le nom spécifié.
	 */
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
			int pageNumber, int pageSize, int statusId, String columnName, String sortingMethod) throws GeneralException;

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
	Integer getNumberOfSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId, String columnName) throws GeneralException;


	/**
	 * Récupère le nombre de la page ou le nouveau sous-traitant est enregistré.
	 *
	 * @param savedSubcontractorId      l'id du nouveau sous-traitant enregistré.
	 * @param pageSize         Le nombre d'éléments par page.
	 * @return Le nombre de page ou le nouveau sous-traitant est enregistré, sinon elle retourne une valeur par défaut égale à 1.
	 */
	int getPageNumberOfNewlyAddedOrUpdatedSubcontractor(int savedSubcontractorId, int pageSize);

}
