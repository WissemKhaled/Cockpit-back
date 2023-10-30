package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	private SubcontractorService subcontractorService;
	private SubcontractorDtoMapper dtoMapper;

	@PostMapping("/add")
	public ResponseEntity<SubcontractorDto> addSubcontractor(@RequestBody SubcontractorDto subcontractorDto) {
		try {
			int savedSubcontractorId = subcontractorService.saveSubcontractor(dtoMapper.dtoToSubcontractor(subcontractorDto));
			Subcontractor savedSubcontractor = subcontractorService.getSubcontractorById(savedSubcontractorId);
			SubcontractorDto savedSubcontractorDto = dtoMapper.subcontractorToDto(savedSubcontractor);
			return ResponseEntity.ok(savedSubcontractorDto);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while adding subcontractor");
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
					.subcontractorToDto(subcontractorService.getSubcontractorById(updatedSubcontractor.getId()));

			return ResponseEntity.ok(modifedSubcontractorDto);

		} catch (Exception e) {
			throw new RuntimeException("Error accured");
		}

	}
}
