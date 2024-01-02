package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.StatusDto;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.service.ServiceProviderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/service-providers")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceProviderController {

	private final ServiceProviderService serviceProviderService;
	
	public ServiceProviderController(ServiceProviderService serviceProviderService) {
		this.serviceProviderService = serviceProviderService;
	}
	
	/**
	 * Récupère prestataire en fonction de son ID.
	 *
	 * @param spId L'ID du prestataire à récupérer.
	 * @return ResponseEntity contenant un ServiceProviderDto si le prestataire est trouvé,
	 *         ResponseEntity avec HttpStatus.NOT_FOUND si le prestataire n'est pas trouvé,
	 *         ResponseEntity avec HttpStatus.BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/{spId}")
	public ResponseEntity<ServiceProviderDto> getServiceProviderById(@PathVariable int spId) {
		try {
			return new ResponseEntity<ServiceProviderDto>(serviceProviderService.getServiceProviderById(spId), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Enregistre ou met à jour un prestataire et renvoie le numéro de la page qui lui correspond.
	 *
	 * @param serviceProviderDto Les données du prestataire à enregistrer ou mettre à jour.
	 * @return ResponseEntity contenant le ServiceProviderDto enregistré, ou mis à jour avec le statut OK et le numéro de la page qui lui correspond,
	 *         ResponseEntity avec HttpStatus.NOT_FOUND si le prestataire n'est pas trouvé lors de la mise à jour,
	 *         ResponseEntity avec HttpStatus.CONFLICT si des données en double sont détectées lors de l'enregistrement,
	 *         ResponseEntity avec HttpStatus.BAD_REQUEST en cas d'erreur.
	 */
	@PostMapping("/save")
	public ResponseEntity<ServiceProviderDto> saveServiceProvider(
			@Valid @RequestBody ServiceProviderDto serviceProviderDto, 
			@RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize) {
	    try {
	        // Formattage des données du prestataire
	        serviceProviderDto.setSpFirstName(serviceProviderService.firstNameAndEmailFormatter(serviceProviderDto.getSpFirstName()));
	        serviceProviderDto.setSpName(serviceProviderService.nameFormatter(serviceProviderDto.getSpName()));
	        serviceProviderDto.setSpEmail(serviceProviderService.firstNameAndEmailFormatter(serviceProviderDto.getSpEmail()));

	        // Vérification de l'existence du prestataire par ID
	        boolean isServiceProviderExist = serviceProviderService.checkIfServiceProviderExistById(serviceProviderDto.getSpId());

	        if (isServiceProviderExist) {
	            // Mise à jour du prestataire
	            serviceProviderService.handleServiceProviderUpdating(serviceProviderDto);
	            serviceProviderService.updateServiceProvider(serviceProviderDto);
                int newPageNumberOfUpdatedServiceProvider = serviceProviderService.getPageNumberOfNewlyAddedOrUpdatedServiceProvider(serviceProviderDto.getSpId(),pageSize);
                ServiceProviderDto updatedServiceProvider = serviceProviderService.getServiceProviderById(serviceProviderDto.getSpId());
                updatedServiceProvider.setNewPage(newPageNumberOfUpdatedServiceProvider);
                return new ResponseEntity<>(updatedServiceProvider, HttpStatus.OK);
	        } else {
	            if (serviceProviderDto.getSpId() > 0) {
	                // Enregistrement d'un nouveau prestataire
	                serviceProviderService.handleServiceProviderSaving(serviceProviderDto);
	                int savedServiceProviderId = serviceProviderService.saveServiceProvider(serviceProviderDto);
	                int pageNumberOfNewlyAddedServiceProvider = serviceProviderService.getPageNumberOfNewlyAddedOrUpdatedServiceProvider(savedServiceProviderId,pageSize);
	                ServiceProviderDto savedServiceProvider = serviceProviderService.getServiceProviderById(savedServiceProviderId);
	                savedServiceProvider.setNewPage(pageNumberOfNewlyAddedServiceProvider);
	                return new ResponseEntity<>(savedServiceProvider ,HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity("Invalid Id", HttpStatus.BAD_REQUEST);
	            }
	        }
	    } catch (EntityNotFoundException e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (EntityDuplicateDataException e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
	    } catch (GeneralException e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	/**
	 * Archive un prestataire en fonction de son ID.
	 *
	 * @param serviceProviderId L'ID du prestataire à archiver.
	 * @return ResponseEntity avec un message indiquant le succès de l'archivage et le statut OK,
	 *         ResponseEntity avec un message d'erreur si le prestataire est déjà archivé et le statut BAD_REQUEST,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@PutMapping("/archive/{serviceProviderId}")
	public ResponseEntity<String> archiveServiceProvider(@PathVariable int serviceProviderId) {
	    try {
	        // Récupération du prestataire par ID
	        ServiceProviderDto serviceProviderDtoToArchive = serviceProviderService.getServiceProviderById(serviceProviderId);

	        // Vérification si le prestataire est déjà archivé
	        if (serviceProviderDtoToArchive.getSpStatus().getStId() == 4) {
	            throw new AlreadyArchivedEntity(String.format("Erreur: le prestataire avec l'id %d est déjà archivé.", serviceProviderId));
	        }

	        serviceProviderService.archiveServiceProvider(serviceProviderDtoToArchive);
	        return new ResponseEntity<>(String.format("Le prestataire avec l'id: %d a été archivé avec succès", serviceProviderId), HttpStatus.OK);
	    } catch (AlreadyArchivedEntity e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	/**
	 * Récupère tous les prestataires associés à un sous-traitant en fonction de son ID.
	 *
	 * @param subcontractorId L'ID du sous-traitant pour lequel récupérer les prestataires.
	 * @return ResponseEntity contenant la liste des ServiceProviderDto associés au sous-traitant avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun prestataire n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@GetMapping("/all-service-providers/{subcontractorId}")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersBySubcontractorId(@PathVariable int subcontractorId) {
	    try {
	        List<ServiceProviderDto> serviceProviders = serviceProviderService.getServiceProvidersBySubcontractorId(subcontractorId);
	        if (serviceProviders.isEmpty()) {
	            throw new EntityNotFoundException(String.format("Le sous-traitant avec l'id: %d n'a pas de prestataires", subcontractorId));
	        }
	        return new ResponseEntity<>(serviceProviders, HttpStatus.OK);
	    } catch (EntityNotFoundException e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	/**
	 * Récupère la liste des prestataires filtrée en fonction du statut.
	 *
	 * @param sortingMethod La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageNumber    Le numéro de la page à récupérer (par défaut : 1).
	 * @param pageSize      Le nombre d'éléments par page (par défaut : 20).
	 * @param statusId      L'ID du statut pour filtrer les prestataires. Si null, exclut les prestataires avec un statut archivé
	 * @return ResponseEntity contenant la liste des ServiceProviderDto filtrés par statut avec le statut OK.
	 */
	@GetMapping("/all-service-providers-with-or-without-status")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersWithOrWithoutStatus(
	        @RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
	        @RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
	        @RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize,
	        @RequestParam(name = "statusId") int statusId
	) {
	    List<ServiceProviderDto> filteredServiceProviders = serviceProviderService.getAllServiceProvidersWithOrWithoutStatus(sortingMethod, pageNumber, pageSize, statusId);

	    return new ResponseEntity<>(filteredServiceProviders, HttpStatus.OK);
	}
	
	/**
	 * Compte le nombre de prestataires filtrés en fonction du statut.
	 *
	 * @param statusId L'ID du statut pour filtrer les prestataires. Si null, exclut les prestataires avec un statut archivé.
	 * @return ResponseEntity contenant le nombre de prestataires filtrés par statut avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si le statut n'est pas trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@GetMapping("/count-all-service-providers-with-or-without-status")
	public ResponseEntity<Integer> countAllServiceProvidersFiltredByStatus(@RequestParam int statusId) {
	    try {
	        // Appel du service pour compter le nombre de prestataires filtrés par statut
	        int serviceProviderCount = serviceProviderService.countAllServiceProvidersWithOrWithoutStatus(statusId);
	        return new ResponseEntity<>(serviceProviderCount, HttpStatus.OK);
	    } catch (EntityNotFoundException e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
	 * Récupère la liste des prestataires filtrée par recherche et statut (si l'ID du statut est non null) ou par recherche seule.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param sortingMethod    La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageNumber       Le numéro de la page à récupérer (par défaut : 1).
	 * @param pageSize         Le nombre d'éléments par page (par défaut : 20).
	 * @param statusId         L'ID du statut pour filtrer les prestataires. Si null, le filtrage par statut est ignoré et les prestataires avec un statut archivé ne sont pas comptés.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return ResponseEntity contenant la liste des ServiceProviderDto filtrés par recherche et statut avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun prestataire n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@GetMapping("/all-service-providers/search")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersBySearchAndStatus(
			@RequestParam(name = "searchTerms") String searchTerms,
			@RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize,
			@RequestParam(name = "statusId") int statusId,
			@RequestParam(name = "columnName") String columnName) {
		try {
	        // Récupération les prestataires filtré par recherche et (facultativement) statut
			List<ServiceProviderDto> filtredServiceProviders= serviceProviderService.getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(searchTerms, pageNumber, pageSize,statusId,columnName);
			if (filtredServiceProviders.isEmpty()) throw new EntityNotFoundException(String.format("Le prestataire avec le nom: %s n'existe pas", searchTerms));
			return new ResponseEntity<>(filtredServiceProviders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Récupère le nombre de prestataires filtré par recherche et statut (si l'ID du statut est non null) ou par recherche seule.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les prestataires.
	 * @param statusId         L'ID du statut pour filtrer les prestataires. Si null, le filtrage par statut est ignoré et les prestataires avec un statut archivé ne sont pas comptés.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "subcontractorName", "firstName", "name", et "email".
	 *                   <ul>
	 *                      <li>"subcontractorName": Nom du sous-traitant affilié.</li>
	 *                      <li>"firstName": Prénom du prestataire.</li>
	 *                      <li>"name": Nom du prestataire.</li>
	 *                      <li>"email": Email du prestataire.</li>
	 *                   </ul>
	 * @return ResponseEntity contenant le nombre de prestataires filtrés par recherche et statut avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun prestataire n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@GetMapping("/count-all-service-providers/search")
	public ResponseEntity<Integer> getNumberOfServiceProvidersBySearchAndStatus(
			@RequestParam(name = "searchTerms") String searchTerms,
			@RequestParam(name = "statusId") int statusId,
			@RequestParam(name = "columnName") String columnName) {
		try {
	        // Récupération du nombre de prestataires filtré par recherche et (facultativement) statut
			Integer numberOfServiceProviders= serviceProviderService.getNumberOfServiceProvidersBySearchAndWithOrWithoutStatusFiltring(searchTerms,statusId, columnName);
			if (numberOfServiceProviders == 0) throw new EntityNotFoundException(String.format("Le prestataire avec le nom: %s et le statusId: %d n'existe pas", searchTerms, statusId));
			return new ResponseEntity<>(numberOfServiceProviders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Récupère la liste de tous les statuts des prestataires.
	 *
	 * @return ResponseEntity contenant la liste des DTO des statuts avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun statut n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/all-status")
	public ResponseEntity<List<StatusDto>> getAllServiceProvider() {
		try {
			return new ResponseEntity<>(serviceProviderService.getAllStatus(), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
