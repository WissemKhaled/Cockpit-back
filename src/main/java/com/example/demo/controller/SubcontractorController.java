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

import com.example.demo.dto.StatusDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.service.SubcontractorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subcontractor")
@CrossOrigin(origins = "http://localhost:4200")
public class SubcontractorController {
	private final SubcontractorService subcontractorService;

	public SubcontractorController(SubcontractorService subcontractorService) {
		this.subcontractorService = subcontractorService;
	}

	/**
	 * Récupère la liste de tous les statuts des sous-traitants.
	 *
	 * @return ResponseEntity contenant la liste des DTO des statuts avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun statut n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/all-status")
	public ResponseEntity<List<StatusDto>> getAllStatus() {
		try {
			return new ResponseEntity<>(subcontractorService.getAllStatus(), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Récupère la liste de tous les sous-traitants avec pagination et tri.
	 *
	 * @param nameColonne Le nom de la colonne à utiliser pour le tri (par défaut :  l'ID du statut "s_fk_status_id").
	 * @param sorting     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageNumber        Le numéro de la page à récupérer (par défaut : 1).
	 * @param pageSize    Le nombre d'éléments par page (par défaut : 10).
	 * @return ResponseEntity contenant la liste des DTO des sous-traitants avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitant n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/all-subcontractors")
	public ResponseEntity<List<SubcontractorDto>> getAllNonArchivedSubcontractors(
			@RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
		try {
			return new ResponseEntity<>(subcontractorService.getAllNonArchivedSubcontractors(sortingMethod, pageNumber, pageSize),HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Récupère le nombre total de sous-traitants.
	 *
	 * @return ResponseEntity contenant le nombre total de sous-traitants avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitant n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/count-all-subcontractors")
	public ResponseEntity<Integer> getNumberOfAllNonArchivedSubcontractors() {
		try {
			return new ResponseEntity<>(subcontractorService.getNumberOfAllNonSubcontractors(), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	/**
	 * Récupère la liste des sous-traitants avec un statut spécifié, avec pagination et tri.
	 *
	 * @param nameColonne Le nom de la colonne à utiliser pour le tri (par défaut : "s_name").
	 * @param sortingMethod     La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageNumber        Le numéro de la page à récupérer (par défaut : 1).
	 * @param pageSize    Le nombre d'éléments par page (par défaut : 10).
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.
	 * @return ResponseEntity contenant la liste des DTO des sous-traitants avec le statut OK,
	 *         ResponseEntity avec un message d'erreur et le statut BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/all-subcontractors/status")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractorWithStatus(
			@RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "statusId", defaultValue = "1") int statusId) {
		try {
			return new ResponseEntity<>(subcontractorService.getAllSubcontractorWithStatus(sortingMethod, pageSize, pageNumber, statusId),
					HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Récupère le nombre total de sous-traitants avec un statut spécifié.
	 *
	 * @param statusId    L'ID du statut pour filtrer les sous-traitants.
	 * @return ResponseEntity contenant le nombre total de sous-traitants avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitant n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut BAD_REQUEST en cas d'erreur.
	 */
	@GetMapping("/count-all-subcontractors/status")
	public ResponseEntity<Integer> getNumberOfAllSubcontractorsWithStatus(
			@RequestParam(name = "statusId") Integer statusId) {
		try {
			return new ResponseEntity<>(subcontractorService.getNumberOfAllSubcontractorsWithStatus(statusId), HttpStatus.OK);
			
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	/**
	 * Récupère les informations d'un sous-traitant en fonction de son ID.
	 *
	 * @param id L'ID du sous-traitant à récupérer.
	 * @return ResponseEntity contenant le DTO du sous-traitant avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si l'ID n'est pas valide et le statut BAD_REQUEST,
	 *         ResponseEntity avec un message d'erreur si le sous-traitant n'est pas trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<SubcontractorDto> getSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			if (parsedId > 0) {
				return new ResponseEntity<>(subcontractorService.getSubcontractorWithStatus(parsedId), HttpStatus.OK);
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			return new ResponseEntity("Id non valide", HttpStatus.BAD_REQUEST);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	/**
	 * Enregistre ou met à jour un sous-traitant. 
	 *
	 * @param subcontractorDto DTO représentant le sous-traitant à enregistrer ou mettre à jour.
	 * @return ResponseEntity contenant le DTO du sous-traitant enregistré ou mis à jour avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si l'ID est invalide et le statut BAD_REQUEST,
	 *         ResponseEntity avec un message d'erreur si le sous-traitant existe déjà et le statut CONFLICT,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	@PostMapping("/save")
	public ResponseEntity<SubcontractorDto> saveSubcontractor(
			@Valid @RequestBody SubcontractorDto subcontractorDto,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
		try {
			if (subcontractorDto.getSId() > 0) {
				boolean isSubcontractorExist = subcontractorService.checkIfSubcontractorExist(subcontractorDto.getSId());
				if (isSubcontractorExist) {
					// si le sous-traitant existe, update
					this.subcontractorService.handleSubcontractorUpdate(subcontractorDto);
					subcontractorService.updateSubcontractor(subcontractorDto);
	                int newPageNumberOfUpdatedSubcontractor = subcontractorService.getPageNumberOfNewlyAddedOrUpdatedSubcontractor(subcontractorDto.getSId(),pageSize);
					SubcontractorDto updatedSubcontractorDto = subcontractorService.getSubcontractorWithStatus(subcontractorDto.getSId());
					updatedSubcontractorDto.setNewPage(newPageNumberOfUpdatedSubcontractor);
					return new ResponseEntity<>(updatedSubcontractorDto, HttpStatus.OK);
				} else {
					try {
					 // si le sous-traitant n'existe pas, save
					 this.subcontractorService.handleSubcontractorSave(subcontractorDto);
					 int savedSubcontractorId = subcontractorService.saveSubcontractor(subcontractorDto);
		             int pageNumberOfNewlyAddedSubcontractor = subcontractorService.getPageNumberOfNewlyAddedOrUpdatedSubcontractor(savedSubcontractorId,pageSize);
					 SubcontractorDto savedSubcontractorDto = subcontractorService.getSubcontractorWithStatus(savedSubcontractorId);
					 savedSubcontractorDto.setNewPage(pageNumberOfNewlyAddedSubcontractor);
					 return new ResponseEntity<>(savedSubcontractorDto, HttpStatus.CREATED);
	                } catch (GeneralException e) {
	                    return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	                }
				}
			} else {
				return new ResponseEntity("Invalid Id", HttpStatus.BAD_REQUEST);
			}
		} catch (EntityDuplicateDataException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Archive un sous-traitant en fonction de son ID.
	 *
	 * @param id L'ID du sous-traitant à archiver.
	 * @return ResponseEntity contenant le DTO du sous-traitant archivé avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si le sous-traitant est déjà archivé et le statut BAD_REQUEST,
	 *         ResponseEntity avec un message d'erreur si le format de l'ID est invalide et le statut NOT_ACCEPTABLE,
	 *         ResponseEntity avec un message d'erreur si le sous-traitant n'est pas trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur en cas d'erreur interne et le statut INTERNAL_SERVER_ERROR.
	 */
	@PutMapping("/archive/{id}")
	public ResponseEntity<String> archiveSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			if (parsedId > 0) {
				SubcontractorDto subcontractortoArchive = subcontractorService.getSubcontractorWithStatus(parsedId);
				if (subcontractortoArchive.getStatus().getStName().equals("Archivé")) {
					throw new AlreadyArchivedEntity(String.format("le sous-traitant avec l'id: %d est déjà archivé", parsedId));
				}
				int isArchived = subcontractorService.archiveSubcontractor(subcontractortoArchive);
				if (isArchived == 0 ) {
					throw new Exception();
				}
				return new ResponseEntity<>(String.format("le sous-traitant avec l'id: %d a été archivé", parsedId), HttpStatus.OK);
			} else {
				throw new NumberFormatException();
			}
		} catch (AlreadyArchivedEntity e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			return new ResponseEntity("format de l'id est invalide.", HttpStatus.NOT_ACCEPTABLE);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Récupère la liste des sous-traitants filtrée par recherche et statut (si l'ID du statut est non null) ou par recherche seule.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param sortingMethod    La méthode de tri, "asc" pour ascendant ou "desc" pour descendant (par défaut : "asc").
	 * @param pageNumber       Le numéro de la page à récupérer (par défaut : 1).
	 * @param pageSize         Le nombre d'éléments par page (par défaut : 20).
	 * @param statusId         L'ID du statut pour filtrer les sous-traitants. Si null, le filtrage par statut est ignoré et les sous-traitants avec un statut archivé ne sont pas comptés.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
	 * @return ResponseEntity contenant la liste des ServiceProviderDto filtrés par recherche et statut avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun sous-traitants n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@GetMapping("/all-subcontractors/search")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractorsBySearchAndStatus(
			@RequestParam(name = "searchTerms") String searchTerms,
			@RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize,
			@RequestParam(name = "statusId") int statusId,
			@RequestParam(name = "columnName") String columnName) {
		try {
	        // Récupération les sous-traitants filtré par recherche et (facultativement) statut
			List<SubcontractorDto> filtredSubcontractors= subcontractorService.getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(searchTerms, pageNumber, pageSize,statusId,columnName,sortingMethod);
			if (filtredSubcontractors.isEmpty()) throw new EntityNotFoundException(String.format("Le sous-traitant avec le %s n'existe pas", searchTerms));
			return new ResponseEntity<>(filtredSubcontractors, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Récupère le nombre de sous-traitants filtré par recherche et statut (si l'ID du statut est non null) ou par recherche seule.
	 *
	 * @param searchTerms      Les termes de recherche pour filtrer les sous-traitants.
	 * @param statusId         L'ID du statut pour filtrer les sous-traitants. Si null, le filtrage par statut est ignoré et les sous-traitants avec un statut archivé ne sont pas comptés.
	 * @param columnName  L'attribut de recherche spécifié parmi la liste suivante : "name", et "email".
	 *                         - "name" : Nom du sous-traitant.
	 *                         - "email" : Email du sous-traitant.
     * @return ResponseEntity contenant le nombre de sous-traitants filtrés par recherche et statut avec le statut OK,
	 *         ResponseEntity avec un message d'erreur si aucun prestataire n'est trouvé et le statut NOT_FOUND,
	 *         ResponseEntity avec un message d'erreur et le statut INTERNAL_SERVER_ERROR en cas d'erreur.
	 */
	@GetMapping("/count-all-subcontractors/search")
	public ResponseEntity<Integer> getNumberOfSubcontractorsBySearchAndStatus(
			@RequestParam(name = "searchTerms") String searchTerms,
			@RequestParam(name = "statusId") int statusId,
			@RequestParam(name = "columnName") String columnName) {
		try {
	        // Récupération du nombre de prestataires filtré par recherche et (facultativement) statut
			Integer numberOfSubcontractors= subcontractorService.getNumberOfSubcontractorsBySearchAndWithOrWithoutStatusFiltring(searchTerms,statusId,columnName);
			System.err.println("here: "+numberOfSubcontractors);
			if (numberOfSubcontractors == 0) throw new EntityNotFoundException(String.format("Le sous-traitant avec le %s et le statusId: %d n'existe pas", searchTerms, statusId));
			return new ResponseEntity<>(numberOfSubcontractors, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}