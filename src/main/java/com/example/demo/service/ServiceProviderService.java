package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.StatusDto;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;

public interface ServiceProviderService {
	
	/**
	 * Récupère un prestataire par son ID, en incluant les informations sur le sous-traitant associé.
	 *
	 * @param serviceProviderId L'ID du prestataire à récupérer.
	 * @return Le prestataire trouvé avec les informations sur le sous-traitant, s'il existe.
	 * @throws EntityNotFoundException Si aucun prestataire n'est trouvé avec l'ID spécifié.
	 */
	ServiceProviderDto getServiceProviderById(int serviceProviderId);
	
	
	/**
	 * Récupère la liste des prestataires associés à un sous-traitant spécifié.
	 *
	 * @param subcontractorId L'ID du sous-traitant pour lequel récupérer les prestataires.
	 * @return Liste des DTO des prestataires associés au sous-traitant.
	 */
	List<ServiceProviderDto> getAllServiceProvidersBySubcontractorId(int subcontractorId);
	
	
	/**
	 *Récupère la liste des prestataires filtrée par recherche et statut (si l'ID du statut est non null) ou par recherche seule, avec prise en compte de la pagination.
	 *
	 * @param sortingMethod La méthode de tri, "asc" pour ascendant ou "desc" pour descendant.
	 * @param pageNumber    Le numéro de la page à récupérer.
	 * @param pageSize      Le nombre d'éléments par page.
	 * @param statusId      L'ID du statut pour filtrer les prestataires (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @return Liste des DTO des prestataires filtrés par statut (si l'ID du statut est non null) ou par recherche seule (si l'ID du statut est null) et paginés.
	 * @throws EntityNotFoundException Si le statut spécifié n'existe pas.
	 */
	List<ServiceProviderDto> getAllServiceProvidersWithOrWithoutStatus(String sortingMethod, int pageNumber, int pageSize, int statusId);
	
	
	/**
	 * Compte le nombre de prestataires filtré par statut.
	 *
	 * @param statusId      L'ID du statut pour filtrer les prestataires (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @return Le nombre de prestataires selon le statut spécifié.
	 * @throws EntityNotFoundException Si le statut spécifié n'existe pas.
	 */
	int countAllServiceProvidersWithOrWithoutStatus(int statusId);
	
	
	/**
	 * Récupère la liste des prestataires filtrée par recherche, statut, attribut de recherche, avec prise en compte de la pagination.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param pageNumber       Le numéro de la page à récupérer.
	 * @param pageSize         Le nombre d'éléments par page.
	 * @param statusId      L'ID du statut pour filtrer les prestataires (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return Liste des DTO des prestataires filtrés par recherche, statut et attribut de recherche, paginés.
	 * @throws GeneralException Si l'attribut de recherche spécifié n'est pas pris en charge.
	 */
	List<ServiceProviderDto> getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int pageNumber,
			int pageSize, int statusId, String columnName) throws GeneralException;
	
	
	/**
	 * Récupère le nombre de prestataires filtré par recherche, statut et attribut de recherche.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param statusId         L'ID du statut pour filtrer les prestataires (0 pour les statuts sauf archivé, 4 pour les archivés, 1 à 3 pour les autres statuts).
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return Le nombre de prestataires selon la recherche, le statut et l'attribut spécifiés.
	 * @throws GeneralException Si l'attribut de recherche spécifié n'est pas pris en charge.
	 */
	int countServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String columnName) throws GeneralException;
	
	
	/**
	 * Récupère la liste paginée et triée des prestataires en fonction des paramètres spécifiés et du statut.
	 *
	 * @param nameColonne La colonne à utiliser pour le tri (par défaut : "s_fk_status_id").
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageSize    Le nombre d'éléments par page (par défaut : 10).
	 * @param page        Le numéro de la page à récupérer (par défaut : 1).
	 * @param statusId    L'ID du statut pour filtrer les prestataires.
	 * @return Liste des DTO des prestataires paginée et triée avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun prestataire n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	List<StatusDto> getAllStatus();
	
	
	/**
	 * Enregistre un nouveau prestataire dans la base de données.
	 *
	 * @param serviceProviderDtoToSave Le prestataire à enregistrer.
	 * @return L'ID du prestataire enregistré, ou 0 si l'enregistrement a échoué.
	 */
	int saveServiceProvider(ServiceProviderDto serviceProviderDtoToSave);
	
	
	/**
	 * Met à jour les informations d'un prestataire dans la base de données.
	 *
	 * @param serviceProviderToUpdate Le prestataire avec les informations à mettre à jour.
	 * @return Le nombre d'enregistrements affectés par la mise à jour.
	 */
	int updateServiceProvider(ServiceProviderDto serviceProviderDtoToUpdate);
	
	
	/**
	 * Archive un prestataire en mettant à jour son statut dans la base de données.
	 *
	 * @param serviceProviderIdToArchive L'ID du prestataire à archiver.
	 * @return 1 si le prestataire a été bien archivé, sinon 0.
	 * @throws AlreadyArchivedEntity Si le prestataire est déjà archivé.
	 * @throws EntityNotFoundException Si le prestataire n'est pas trouvé.
	 */
	int archiveServiceProvider(int serviceProviderId) throws AlreadyArchivedEntity;

	
	/**
	 * Vérifie l'existence d'un prestataire en utilisant son ID.
	 *
	 * @param serviceProviderId L'ID du prestataire à vérifier.
	 * @return true si le prestataire existe, sinon false.
	 */
	boolean checkIfServiceProviderExistById(int serviceProviderId);

	
	/**
	 * Gère la mise à jour d'un prestataire, vérifiant la duplication des données basée sur l'email.
	 *
	 * @param serviceProviderDto Le DTO du prestataire à mettre à jour.
	 * @throws EntityDuplicateDataException Si l'email du prestataire saisi existe déjà pour un autre prestataire.
	 */
	void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto);

	
	/**
	 * Gère l'enregistrement d'un nouveau prestataire, vérifiant la duplication des données basée sur l'email.
	 *
	 * @param serviceProviderDto Le DTO du prestataire à enregistrer.
	 * @throws EntityDuplicateDataException Si l'email du prestataire saisi existe déjà pour un autre prestataire.
	 */
	void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto);

	
	/**
	 * Vérifie l'existence d'un prestataire en utilisant son email.
	 *
	 * @param serviceProviderSpEmail L'email du prestataire à vérifier.
	 * @return L'ID du prestataire s'il existe, sinon 0.
	 */
	int checkIfSubcontractorExistBySpEmail(String serviceProviderSpEmail);

	
	/**
	 * Récupère le nombre de la page ou le nouveau prestataire est enregistré.
	 *
	 * @param savedServiceProviderId      l'id du nouveau prestataire enregistré.
	 * @param pageSize         Le nombre d'éléments par page.
	 * @return Le nombre de page ou le nouveau prestataire est enregistré, sinon elle retourne une valeur par défaut égale à 1.
	 */
	int getPageNumberOfNewlyAddedOrUpdatedServiceProvider(int savedServiceProviderId, int pageSize);

	
	/**
	 * Formater le nom, prénom et l'émail du prestatire DTO passé en paramétre.
	 *
	 * @param serviceProviderDto le prestataire dto à formater.
	 * @throws GeneralException Si l'une des données à formatter est nul ou vide.
	 */
	void formattingServiceProviderData(ServiceProviderDto serviceProviderDto) throws GeneralException;

}
