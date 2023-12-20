package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.dto.mapper.SubcontractorDtoMapper;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.SubcontractorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/subcontractor")
@CrossOrigin(origins = "http://localhost:4200")
public class SubcontractorController {
	private final SubcontractorService subcontractorService;

	@Qualifier("userDetailsService")
	private final UserDetailsService userDetailsService;

	public SubcontractorController(SubcontractorService subcontractorService, UserDetailsService userDetailsService) {
		this.subcontractorService = subcontractorService;
		this.userDetailsService = userDetailsService;
	}

	// ce code permet de renvoyer la liste des sous-traitants la methode
	// getAllSubcontractor prend en paramettre pour le tri le nom de la colonne et
	// le type de tri et pour la pagination le nombre d'élement a aficcher et la
	// page en question
	@GetMapping("/all-subcontractors")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractors(
			@RequestParam(name = "nameColonne", defaultValue = "s_fk_status_id", required = false) String nameColonne,
			@RequestParam(name = "sorting", defaultValue = "asc", required = false) String sorting,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
		try {
			return new ResponseEntity<>(subcontractorService.getAllSubcontractors(nameColonne, sorting, page, pageSize),HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// ce code permet de renvoyer le nombre max de sous-traitans
	@GetMapping("/count-all-subcontractors")
	public ResponseEntity<Integer> getNumberOfAllSubcontractors() {
		try {
			return new ResponseEntity<>(subcontractorService.getNumberOfAllSubcontractors(), HttpStatus.OK);
			
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/all-subcontractors/status")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractorWhitStatus(
			@RequestParam(name = "nameColonne", defaultValue = "s_name", required = false) String nameColonne,
			@RequestParam(name = "sorting", defaultValue = "asc", required = false) String sorting,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "statusId") int statusId) {
		try {
			return new ResponseEntity<>(
					subcontractorService.getAllSubcontractorWhitStatus(nameColonne, sorting, pageSize, page, statusId),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/count-all-subcontractors/status")
	public ResponseEntity<Integer> getNumberOfAllSubcontractorsWithStatus(
			@RequestParam(name = "statusId") Integer statusId) {
		try {
			return new ResponseEntity<>(subcontractorService.countTotalItemWhitStatus(statusId), HttpStatus.OK);
			
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all-status")
	public ResponseEntity<List<StatusDto>> getAllSubcontractor() {
		try {
			return new ResponseEntity<>(subcontractorService.getAllStatus(), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// méthode pour récuperer un sous-traitant s'il existe, sinon elle retourn un
	// error 404
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

	// méthode pour inserer un sous-traitant s'il n'existe pas dans la BDD, sinon on
	// le modifie
	@PostMapping("/save")
	public ResponseEntity<SubcontractorDto> saveSubcontractor(@Valid @RequestBody SubcontractorDto subcontractorDto) {
		try {
			if (subcontractorDto.getSId() > 0) {
				boolean isSubcontractorExist = subcontractorService.checkIfSubcontractorExist(subcontractorDto.getSId());
				if (isSubcontractorExist) {
					// si le sous-traitant existe, update
					this.subcontractorService.handleSubcontractorUpdate(subcontractorDto);
					subcontractorService.updateSubcontractor(subcontractorDto);
					SubcontractorDto updatedSubcontractorDto = subcontractorService.getSubcontractorWithStatus(subcontractorDto.getSId());
					return new ResponseEntity<>(updatedSubcontractorDto, HttpStatus.OK);
				} else {
					// si le sous-traitant n'existe pas, save
					this.subcontractorService.handleSubcontractorSave(subcontractorDto);
					int savedSubcontractorId = subcontractorService.saveSubcontractor(subcontractorDto);
					SubcontractorDto savedSubcontractorDto = subcontractorService.getSubcontractorWithStatus(savedSubcontractorId);
					return new ResponseEntity<>(savedSubcontractorDto, HttpStatus.CREATED);
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

	// methode pour archiver le sous-traitant
	@PutMapping("/archive/{id}")
	public ResponseEntity<SubcontractorDto> archiveSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			if (parsedId > 0) {
				SubcontractorDto subcontractortoArchive = subcontractorService.getSubcontractorWithStatus(parsedId);
				if (subcontractortoArchive.getStatus().getStName().equals("Archivé")) {
					throw new AlreadyArchivedEntity(
							String.format("le sous-traitant avec l'id: %d est déjà archivé", parsedId));
				}
				subcontractorService.archiveSubcontractor(subcontractortoArchive);
				return new ResponseEntity<>(subcontractorService.getSubcontractorWithStatus(parsedId), HttpStatus.OK);
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
			List<SubcontractorDto> filtredSubcontractors= subcontractorService.getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(searchTerms, pageNumber, pageSize,statusId,columnName);
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
			Integer numberOfSubcontractors= subcontractorService.getNumberOfSubcontractorsBySearchAndWithOrWithoutStatusFiltring(searchTerms,statusId, columnName);
			if (numberOfSubcontractors == 0) throw new EntityNotFoundException(String.format("Le sous-traitant avec le %s et le statusId: %d n'existe pas", searchTerms, statusId));
			return new ResponseEntity<>(numberOfSubcontractors, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}