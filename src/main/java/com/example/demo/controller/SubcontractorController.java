package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

@RestController
@RequestMapping("/subcontractor")
@AllArgsConstructor
public class SubcontractorController {

	private final SubcontractorService subcontractorService;
	private final SubcontractorDtoMapper dtoMapper;

	@GetMapping("/{id}")
	public ResponseEntity<?> getSubcontractor(@PathVariable int id) {
	    Subcontractor subcontractor = subcontractorService.getSubcontractorWithStatus(id);
	    
	    if (subcontractor == null) {
	        return new ResponseEntity<>("Subcontractor not found",HttpStatus.NOT_FOUND);
	    }
	    
	    return new ResponseEntity<>(subcontractor, HttpStatus.OK);
	}


	@PostMapping("/add")
	public ResponseEntity<?> addOrUpdateSubcontractor(@RequestBody SubcontractorDto subcontractorDto) {
		try {
			int savedSubcontractorId = subcontractorService
					.saveSubcontractor(dtoMapper.dtoToSubcontractor(subcontractorDto));
			Subcontractor savedSubcontractor = subcontractorService.getSubcontractorById(savedSubcontractorId);
			SubcontractorDto savedSubcontractorDto = dtoMapper.subcontractorToDto(savedSubcontractor);
			subcontractorService.saveSubcontractor(savedSubcontractor);
			return ResponseEntity.ok(savedSubcontractorDto);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSubcontractor(@PathVariable int id,
			@RequestBody SubcontractorDto updatedSubcontractorDto) {
		try {
			Subcontractor existingSubcontractor = subcontractorService.getSubcontractorById(id);
			if (existingSubcontractor == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			updatedSubcontractorDto.setSId(existingSubcontractor.getSId());
			Subcontractor updatedSubcontractor = dtoMapper.dtoToSubcontractor(updatedSubcontractorDto);
			subcontractorService.updateSubcontractor(updatedSubcontractor);
			System.err.println(updatedSubcontractor.getSId());
			SubcontractorDto modifedSubcontractorDto = dtoMapper
					.subcontractorToDto(subcontractorService.getSubcontractorById(updatedSubcontractor.getSId()));

			return ResponseEntity.ok(modifedSubcontractorDto);

		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
}
