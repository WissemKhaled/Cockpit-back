package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.ServiceProvider;

@Mapper
public interface ServiceProviderMapper {
	
	/**
	 * Récupère un prestataire par son ID.
	 *
	 * @param serviceProviderId L'ID du prestataire à récupérer.
	 * @return Le prestataire s'il existe.
	 */
	ServiceProvider findServiceProviderById(int spId);
	
	
	/**
	 * Récupère un prestataire associés à son sous-traitant .
	 *
	 * @param sId L'ID du prestataire à récupérer avec son sous-traitant et statut.
	 * @return le prestataire avec son sous-traitant et statut, s'il existe.
	 */
	ServiceProvider findServiceProviderWithSubcontractorBySpId(int sId);
	
	
	/**
	 *Récupère la liste des prestataires filtrée par recherche seule, avec prise en compte de la pagination.
	 *
	 * @param sortingMethod La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param pageNumber    Le numéro de la page à récupérer.
	 * @param pageSize      Le nombre d'éléments par page.
	 * @return Liste des prestataires paginés.
	 */
	List<ServiceProvider> findAllNonArchivedServiceProviders(
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize);
	
	
	/**
	 *Récupère la liste des prestataires filtrée par recherche et statut, avec prise en compte de la pagination.
	 *
	 * @param sortingMethod La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param pageNumber    Le numéro de la page à récupérer.
	 * @param pageSize      Le nombre d'éléments par page.
	 * @param statusId      L'ID du statut pour filtrer les prestataires ( 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @return Liste des prestataires filtrés par statut et paginés.
	 */
	List<ServiceProvider> findAllServiceProvidersFlitredByStatus(
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize, 
			@Param("statusId") int statusId);
	
	
	/**
	 * Compte le nombre de prestataires non archivés.
	 *
	 * @return Le nombre de prestataires non archivés.
	 */
	int countAllNonArchivedServiceProviders();
	
	
	/**
	 * Compte le nombre de prestataires filtré par statut.
	 *
	 * @param statusId      L'ID du statut pour filtrer les prestataires (4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @return Le nombre de prestataires selon le statut spécifié.
	 */
	int countAllServiceProvidersFiltredByStatus(int statusId);
	
	
	/**
	 * Récupère la liste des prestataires filtrée par recherche, attribut de recherche avec prise en compte de la pagination.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param pageNumber       Le numéro de la page à récupérer.
	 * @param pageSize         Le nombre d'éléments par page.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return Liste des prestataires filtrés par recherche et paginés.
	 */
	List<ServiceProvider> findServiceProvidersByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize") int offset,
			@Param("offset") int pageSize);
	
	
	/**
	 * Récupère la liste des prestataires filtrée par recherche, statut, attribut de recherche, avec prise en compte de la pagination.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param pageNumber       Le numéro de la page à récupérer.
	 * @param pageSize         Le nombre d'éléments par page.
	 * @param statusId      L'ID du statut pour filtrer les prestataires (4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return Liste des prestataires filtrés par recherche, statut et attribut de recherche, paginés.
	 */
	List<ServiceProvider> findServiceProvidersByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize, 
			@Param("statusId") int statusId);
	
	
	/**
	 * Récupère le nombre de prestataires filtré par recherche et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return Le nombre de prestataires selon la recherche et l'attribut spécifiés.
	 */
	int countServiceProvidersByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms);

	
	/**
	 * Récupère le nombre de prestataires filtré par recherche, statut et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param statusId         L'ID du statut pour filtrer les prestataires (4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return Le nombre de prestataires selon la recherche, le statut et l'attribut spécifiés.
	 */
	int countServiceProvidersByByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("statusId") int statusId);
	
	
	/**
	 * Enregistre un nouveau prestataire dans la base de données.
	 *
	 * @param serviceProviderDtoToSave Le prestataire à enregistrer.
	 * @return L'ID du prestataire enregistré, ou 0 si l'enregistrement a échoué.
	 */
	int insertServiceProvider(ServiceProvider serviceProvider);
	
	
	/**
	 * Met à jour les informations d'un prestataire dans la base de données.
	 *
	 * @param serviceProviderToUpdate Le prestataire avec les informations à mettre à jour.
	 * @return Le nombre d'enregistrements affectés par la mise à jour.
	 */
	int updateServiceProvider(ServiceProvider serviceProvider);
	
	
	/**
	 * Archive un prestataire en mettant à jour son statut dans la base de données.
	 *
	 * @param serviceProviderIdToArchive L'ID du prestataire à archiver.
	 * @return 1 si le prestataire a été bien archivé, sinon 0.
	 */
	int archiveServiceProvider(ServiceProvider serviceProvider);
	
	
	
	/**
	 * Récupère un prestataire par son émail, en incluant les informations sur le sous-traitant associé.
	 *
	 * @param spEmail L'émail du prestataire à récupérer.
	 * @return Le prestataire trouvé (avec seulement son id et son émail), s'il existe.
	 */
	ServiceProvider findServiceProviderBySpEmail(String spEmail);

	
	/**
	 * Récupère la liste des prestataires associés à un sous-traitant spécifié.
	 *
	 * @param subcontractorId L'ID du sous-traitant pour lequel récupérer les prestataires.
	 * @return Liste des prestataires associés au sous-traitant.
	 */
	List<ServiceProvider> findServiceProvidersBySubcontractorId(int sId);
	
	
	/**
	 * Compter le nombre d'alerts par status pour un prestataire.
	 *
	 * @param serviceProviderId l'id du prestataire.
	 * @return une liste d'entiers dans cette forme [n1,n2,n3] avec:
	 * 					 <ul>
	 *                      <li>n1: nombre d'alerts par le status "En cours".</li>
	 *                      <li>n2: nombre d'alerts par le status "En validation".</li>
	 *                      <li>n3: nombre d'alerts par le status "Validé".</li>
	 *                   </ul>
	 */
	List<Integer> countAllServiceProviderAlerts(int servicePorividerId);

}
