package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private SubcontractorService subcontractorService;
	@Autowired
	private SubcontractorDtoMapper dtoMapper;

	// debut hamza : ce code permet de renvoyer la liste des soustraitan
	// la methode getAllSubcontractor prend en paramettre
	// pour le tri le nom de la colonne et le type de tri
	// et pour la pagination le nombre déelement a aficcher et la page en question
	@GetMapping("/getAll")
	public ResponseEntity<List<SubcontractorDto>> getAllSubcontractor(@RequestParam("nameColonne") String nameColonne,
			@RequestParam("sorting") String sorting, @RequestParam("pageSize") int page,
			@RequestParam("page") int pageSize) {
		System.err.println(nameColonne);
		try {
			log.info("Affiche tous les sous-traitans");
			return new ResponseEntity<>(subcontractorService.getAllSubcontractor(nameColonne, sorting, page, pageSize),HttpStatus.OK);
			

		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	// debut hamza : ce code perùer de renvoyer le nombre max de page en fonction de
	// l'affichage
	@GetMapping("/getAllPages")
	public ResponseEntity<Integer> getAllPages(@RequestParam("pageSize") int pageSize) {
		System.out.println(pageSize);
		try {
			return new ResponseEntity<>(subcontractorService.getNumbersOfPages(pageSize), HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	// fin

	@PostMapping("/add")
	public ResponseEntity<?> addSubcontractor(@RequestBody SubcontractorDto subcontractorDto) {
		try {
			int savedSubcontractorId = subcontractorService
					.saveSubcontractor(dtoMapper.dtoToSubcontractor(subcontractorDto));
			Subcontractor savedSubcontractor = subcontractorService.getSubcontractorById(savedSubcontractorId);
			SubcontractorDto savedSubcontractorDto = dtoMapper.subcontractorToDto(savedSubcontractor);
			return ResponseEntity.ok(savedSubcontractorDto);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<SubcontractorDto> updateSubcontractor(@PathVariable int id,
			@RequestBody SubcontractorDto updatedSubcontractorDto) {
		try {
//            updatedSubcontractor.setId(id); // Assure que l'ID est bien défini
			Subcontractor existingSubcontractor = subcontractorService.getSubcontractorById(id);
			if (existingSubcontractor == null) {
				return ResponseEntity.notFound().build();
			}
			Subcontractor updatedSubcontractor = dtoMapper.dtoToSubcontractor(updatedSubcontractorDto);
			subcontractorService.updateSubcontractor(updatedSubcontractor);
			SubcontractorDto modifedSubcontractorDto = dtoMapper
					.subcontractorToDto(subcontractorService.getSubcontractorById(updatedSubcontractor.getSId()));

			return ResponseEntity.ok(modifedSubcontractorDto);

		} catch (Exception e) {
			throw new RuntimeException("Error accured");
		}

	}
}
