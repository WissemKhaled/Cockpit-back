package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.AlreadyArchivedSubcontractor;
import com.example.demo.exception.SubcontractorNotFoundException;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.service.SubcontractorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/subcontractor")
@AllArgsConstructor
public class SubcontractorController {

	private final SubcontractorService subcontractorService;
	private final SubcontractorDtoMapper dtoMapper;

	@GetMapping("/{id}")
	public ResponseEntity<?> getSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			Subcontractor subcontractor = subcontractorService.getSubcontractorWithStatus(parsedId);
			return new ResponseEntity<>(subcontractor, HttpStatus.FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Invalid ID format. Please provide a valid integer ID.",
					HttpStatus.BAD_REQUEST);
		} catch (SubcontractorNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// méthode pour inserer un sous-traitant s'il n'existe pas dans la BDD, sinon on
	// le modifier
	@PostMapping("/save")
	public ResponseEntity<?> saveSubcontractor(@RequestBody SubcontractorDto subcontractorDto) {
		try {
			if (subcontractorDto.getSId() > 0) {
				subcontractorService.getSubcontractorWithStatus(subcontractorDto.getSId());

				// If the subcontractor exists, update
				Subcontractor subcontractorToUpdate = dtoMapper.dtoToSubcontractor(subcontractorDto);
				subcontractorService.updateSubcontractor(subcontractorToUpdate);
				Subcontractor updatedSubcontractor = subcontractorService
						.getSubcontractorWithStatus(subcontractorToUpdate.getSId());
				SubcontractorDto updatedSubcontractorDto = dtoMapper.subcontractorToDto(updatedSubcontractor);
				return new ResponseEntity<>(updatedSubcontractorDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invalid Id", HttpStatus.BAD_REQUEST);
			}
		} catch (SubcontractorNotFoundException e) {
			int savedSubcontractorId = subcontractorService
					.saveSubcontractor(dtoMapper.dtoToSubcontractor(subcontractorDto));
			Subcontractor savedSubcontractor = subcontractorService.getSubcontractorWithStatus(savedSubcontractorId);
			SubcontractorDto savedSubcontractorDto = dtoMapper.subcontractorToDto(savedSubcontractor);
			return new ResponseEntity<>(savedSubcontractorDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/archive/{id}")
	public ResponseEntity<?> archiveSubcontractor(@PathVariable String id) {
		try {
			int parsedId = Integer.parseInt(id);
			if (parsedId > 0) {
				Subcontractor subcontractortoArchive = subcontractorService.getSubcontractorWithStatus(parsedId);
				if (subcontractortoArchive.getStatus().getStName().equals("ARCHIVE")) {
					throw new AlreadyArchivedSubcontractor(
							String.format("le sout-traitant avec l'id: %d est déjà archivé", parsedId));
				}
				subcontractorService.archiveSubcontractor(subcontractortoArchive);
				return new ResponseEntity<>("le sout-traitant est archivé avec succés", HttpStatus.OK);
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("format de l'id est invalide.", HttpStatus.BAD_REQUEST);
		} catch (SubcontractorNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
