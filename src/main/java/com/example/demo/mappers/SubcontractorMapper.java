package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {

	/**
	 * Récupère le nombre total de sous-traitants enregistrés.
	 *
	 * @return Le nombre total de sous-traitants.
	 */
	Integer countAllNonArchivedSubcontractors();
		
	
	/**
	 * Récupère le nombre total de sous-traitants enregistrés filtrés par statut.
	 * 
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.
	 * @return Le nombre total de sous-traitants filtrés par statut.
	 */
	Integer countAllNonArchivedSubcontractorsWithStatus(@Param("idStatus") Integer idStatus);

	
	/**
	 * Récupère la liste paginée et triée des sous-traitants non archivés en fonction des paramètres spécifiés.
	 *
	 * @param nameColonne La colonne à utiliser pour le tri (par défaut : "s_fk_status_id").
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param page        Le numéro de la page à récupérer.
	 * @param pageSize    Le nombre d'éléments par page.
	 * @return Liste des sous-traitants non archivés paginée et triée.
	 */
	List<Subcontractor> findAllNonArchivedSubcontractors(
			@Param("sortingMethod") String sortingMethod,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize);
	
	
	/**
	 * Récupère la liste paginée et triée des sous-traitants en fonction des paramètres spécifiés et du statut.
	 *
	 * @param nameColonne La colonne à utiliser pour le tri.
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param pageSize    Le nombre d'éléments par page.
	 * @param page        Le numéro de la page à récupérer.
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.
	 * @return Liste des sous-traitants paginée et triée.
	 */
	List<Subcontractor> findAllSubcontractorsWithStatus(
			@Param("sortingMethod") String sortingMethod, 
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize,
			@Param("statusId") int statusId);

	
	/**
	 * Récupère un sous-traitant avec son statut à partir de l'ID.
	 *
	 * @param sId L'ID du sous-traitant.
	 * @return Le sous-traitant avec son statut.
	 */
	Subcontractor findSubcontractorWithStatusById(int sId);

	
	/**
	 * Récupère un sous-traitant en fonction de son nom.
	 *
	 * @param sName Le nom du sous-traitant à récupérer.
	 * @return Le sous-traitant trouvé.
	 */
	Subcontractor findSubcontractorWithStatusBySubcontractorName(@Param("sName") String sName);

	
	/**
	 * Récupère un sous-traitant en fonction de son émail.
	 *
	 * @param sEmail L'émail du sous-traitant à récupérer.
	 * @return Le sous-traitant trouvé.
	 */
	Subcontractor findSubcontractorWithStatusBySubcontractorEmail(@Param("sEmail") String sEmail);

	
	/**
	 * Enregistre un nouveau sous-traitant.
	 *
	 * @param subcontractorDto Le DTO du sous-traitant à enregistrer.
	 * @return L'ID du sous-traitant enregistré ou -1 en cas d'échec.
	 */
	int insertSubcontractor(Subcontractor subcontractor);

	
	/**
	 * Met à jour les informations d'un sous-traitant.
	 *
	 * @param subcontractorDto Le DTO du sous-traitant à mettre à jour.
	 * @return Un indicateur de succès (par exemple, 1 pour succès, 0 pour échec).
	 */
	int updateSubcontractor(Subcontractor subcontractor);

	
	/**
	 * Archive un sous-traitant et archive également ses prestataires de services associés.
	 *
	 * @param subcontractorDtoToArchive Le DTO du sous-traitant à archiver.
	 * @return Un indicateur de succès (par exemple, 1 pour succès, 0 pour échec).
	 */
	int archiveSubcontractor(Subcontractor subcontractorToArchive);


	/**
	 * Récupère le nombre de sous-traitants filtré par recherche, statut et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param statusId         L'ID du statut pour filtrer les sous-traitants (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param pageSize    Le nombre d'éléments par page.
	 * @param page        Le numéro de la page à récupérer (par défaut : 1).                    
	 * @return Le nombre de sous-traitants selon la recherche, le statut et l'attribut spécifiés.
	 */
	List<Subcontractor> findAllSubcontractorsByCriteria(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize")int offset,
			@Param("offset") int pageSize,
			@Param("sortingMethod") String sortingMethod);
	
	
	/**
	 * Récupère le nombre de sous-traitants filtré par recherche, statut et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param statusId         L'ID du statut pour filtrer les sous-traitants (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param pageSize    Le nombre d'éléments par page.
	 * @param page        Le numéro de la page à récupérer (par défaut : 1). 
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.                   
	 * @return Le nombre de sous-traitants selon la recherche, le statut et l'attribut spécifiés.
	 */
	List<Subcontractor> findAllSubcontractorsByCriteriaAndFiltredByStatus(
	        @Param("columnName") String columnName,
	        @Param("searchTerms") String searchTerms,
	        @Param("pageSize") int offset,
	        @Param("offset") int pageSize,
	        @Param("sortingMethod") String sortingMethod,
	        @Param("statusId") int statusId);

	
	/**
	 * Récupère le nombre de sous-traitants filtré par recherche et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @return Le nombre de sous-traitants selon la recherche et l'attribut spécifiés.
	 */
	Integer countAllSubcontractorsByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms);
	
	
	/**
	 * Récupère le nombre de sous-traitants filtré par recherche, statut et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param statusId         L'ID du statut pour filtrer les sous-traitants (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @return Le nombre de sous-traitants selon la recherche, le statut et l'attribut spécifiés.
	 */
	Integer countAllSubcontractorsByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("statusId") int statusId);
}