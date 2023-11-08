package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.exception.SubcontractorNotFoundException;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.service.SubcontractorService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/subcontractor")
@AllArgsConstructor
@Log
public class SubcontractorController {

	private final SubcontractorService subcontractorService;

	private final SubcontractorDtoMapper dtoMapper;

	// debut hamza : ce code permet de renvoyer la liste des soustraitan
	// la methode getAllSubcontractor prend en paramettre
	// pour le tri le nom de la colonne et le type de tri
	// et pour la pagination le nombre déelement a aficcher et la page en question
	@GetMapping("/getAll")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractor(@RequestParam("nameColonne") String nameColonne,
																	  @RequestParam("sorting") String sorting, 
																	  @RequestParam("pageSize") int page,
																	  @RequestParam("page") int pageSize)
	{
		
		try {
			return new ResponseEntity<>(subcontractorService.getAllSubcontractor(nameColonne, sorting, page, pageSize),
					HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	// debut hamza : ce code perùer de renvoyer le nombre max de page en fonction de
	// l'affichage
	@GetMapping("/getAllPages")
	public ResponseEntity<Integer> getAllPages(@RequestParam("pageSize") int pageSize) {
		try {
			return new ResponseEntity<>(subcontractorService.getNumbersOfPages(pageSize), HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

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
				Subcontractor subcontractor = subcontractorService
						.getSubcontractorWithStatus(subcontractorDto.getSId());

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

}
