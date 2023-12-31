package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
	private final SubcontractorDtoMapper subcontractorDtoMapper;

	@Qualifier("userDetailsService")
	private final UserDetailsService userDetailsService;

	public SubcontractorController(SubcontractorService subcontractorService,
			SubcontractorDtoMapper subcontractorDtoMapper, UserDetailsService userDetailsService) {
		this.subcontractorService = subcontractorService;
		this.subcontractorDtoMapper = subcontractorDtoMapper;
		this.userDetailsService = userDetailsService;
	}

	// ce code permet de renvoyer la liste des sous-traitants la methode
	// getAllSubcontractor prend en paramettre pour le tri le nom de la colonne et
	// le type de tri et pour la pagination le nombre d'élement a aficcher et la
	// page en question
	@GetMapping("/getAll")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractor(
			@RequestParam(name = "nameColonne", defaultValue = "s_fk_status_id", required = false) String nameColonne,
			@RequestParam(name = "sorting", defaultValue = "asc", required = false) String sorting,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
		try {
			return new ResponseEntity<>(subcontractorService.getAllSubcontractor(nameColonne, sorting, page, pageSize),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getAllSubcontractors")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractors() {
		try {
			return new ResponseEntity<>(subcontractorService.getAllSubcontractors().stream()
					.map(subcontractorDtoMapper::subcontractorToDto).toList(), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getAllWhitStatus")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractorWhitStatus(
			@RequestParam(name = "nameColonne", defaultValue = "s_name", required = false) String nameColonne,
			@RequestParam(name = "sorting", defaultValue = "asc", required = false) String sorting,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "statusId", required = false) int statusId) {
		try {
			return new ResponseEntity<>(
					subcontractorService.getAllSubcontractorWhitStatus(nameColonne, sorting, pageSize, page, statusId),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getAllStatus")
	public ResponseEntity<List<Status>> getAllSubcontractor() {
		try {
			return new ResponseEntity<>(subcontractorService.getAllStatus(), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// ce code perùer de renvoyer le nombre max de page en fonction de l'affichage
	@GetMapping("/getAllPages")
	public ResponseEntity<Integer> getAllPages() {
		try {
			return new ResponseEntity<>(subcontractorService.getNumbersOfPages(), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// ce code permet de renvoyer le nombre max de sous-traitans
	@GetMapping("/getCountAll")
	public ResponseEntity<Integer> getCountAll(HttpServletRequest request) {
		try {
			return new ResponseEntity<>(subcontractorService.getNumbersOfSubContractor(), HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/getCountByStatus")
	public ResponseEntity<Integer> getCountAllByStatus(
			@RequestParam(name = "statusId", required = true) Integer statusId) {
		try {
			return new ResponseEntity<>(subcontractorService.countTotalItemWhitStatus(statusId), HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	// méthode pour récuperer un sous-traitant s'il existe, sinon elle retourn un
	// error 404
	@GetMapping("/{id}")
	public ResponseEntity<?> getSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			if (parsedId > 0) {
				Subcontractor subcontractor = subcontractorService.getSubcontractorWithStatus(parsedId);
				return new ResponseEntity<>(subcontractor, HttpStatus.OK);
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
	public ResponseEntity<?> saveSubcontractor(@Valid @RequestBody SubcontractorDto subcontractorDto) {
		try {
			if (subcontractorDto.getSId() > 0) {
				boolean isSubcontractorExist = subcontractorService
						.checkIfSubcontractorExist(subcontractorDto.getSId());
				if (isSubcontractorExist) {
					// si le sous-traitant existe, update
					this.subcontractorService.handleSubcontractorUpdate(subcontractorDto);
					Subcontractor subcontractorToSaveOrUpdate = subcontractorDtoMapper
							.dtoToSubcontractor(subcontractorDto);
					subcontractorService.updateSubcontractor(subcontractorToSaveOrUpdate);
					Subcontractor updatedSubcontractor = subcontractorService
							.getSubcontractorWithStatus(subcontractorToSaveOrUpdate.getSId());
					SubcontractorDto updatedSubcontractorDto = subcontractorDtoMapper
							.subcontractorToDto(updatedSubcontractor);
					return new ResponseEntity<>(updatedSubcontractorDto, HttpStatus.OK);
				} else {
					// si le sous-traitant n'existe pas, save
					this.subcontractorService.handleSubcontractorSave(subcontractorDto);
					Subcontractor subcontractorToSaveOrUpdate = subcontractorDtoMapper
							.dtoToSubcontractor(subcontractorDto);
					int savedSubcontractorId = subcontractorService.saveSubcontractor(subcontractorToSaveOrUpdate);
					Subcontractor savedSubcontractor = subcontractorService
							.getSubcontractorWithStatus(savedSubcontractorId);
					SubcontractorDto savedSubcontractorDto = subcontractorDtoMapper
							.subcontractorToDto(savedSubcontractor);
					return new ResponseEntity<>(savedSubcontractorDto, HttpStatus.CREATED);
				}
			} else {
				return new ResponseEntity<>("Invalid Id", HttpStatus.BAD_REQUEST);
			}
		} catch (EntityDuplicateDataException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// methode pour archiver le sous-traitant
	@PutMapping("/archive/{id}")
	public ResponseEntity<?> archiveSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			if (parsedId > 0) {
				Subcontractor subcontractortoArchive = subcontractorService.getSubcontractorWithStatus(parsedId);
				if (subcontractortoArchive.getStatus().getStname().equals("Archivé")) {
					throw new AlreadyArchivedEntity(
							String.format("le sous-traitant avec l'id: %d est déjà archivé", parsedId));
				}
				subcontractorService.archiveSubcontractor(subcontractortoArchive);
				return new ResponseEntity<>(subcontractorService.getSubcontractorWithStatus(parsedId), HttpStatus.OK);
			} else {
				throw new NumberFormatException();
			}
		} catch (AlreadyArchivedEntity e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("format de l'id est invalide.", HttpStatus.NOT_ACCEPTABLE);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}