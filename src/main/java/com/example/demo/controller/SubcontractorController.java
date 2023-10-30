package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	private final SubcontractorService subcontractorService;
	private final SubcontractorDtoMapper dtoMapper;

	@GetMapping("/{id}")
	public ResponseEntity<?> getSubcontractor(@PathVariable int id) {
		Subcontractor subcontractor = subcontractorService.getSubcontractorWithStatus(id);

		if (subcontractor == null) {
			return new ResponseEntity<>("Subcontractor not found", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(subcontractor, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<?> saveSubcontractor(@RequestBody SubcontractorDto subcontractorDto) {
		try {
			if (subcontractorDto.getSId() > 0
					&& subcontractorService.getSubcontractorWithStatus(subcontractorDto.getSId()) != null) {
				Subcontractor subcontractorToUpdate = dtoMapper.dtoToSubcontractor(subcontractorDto);
				subcontractorService.updateSubcontractor(subcontractorToUpdate);
				Subcontractor updatedSubcontractor = subcontractorService
						.getSubcontractorById(subcontractorToUpdate.getSId());
				SubcontractorDto updatedSubcontractorDto = dtoMapper.subcontractorToDto(updatedSubcontractor);
				return new ResponseEntity<>(updatedSubcontractorDto, HttpStatus.OK);
			} else {
				int savedSubcontractorId = subcontractorService
						.saveSubcontractor(dtoMapper.dtoToSubcontractor(subcontractorDto));
				Subcontractor savedSubcontractor = subcontractorService.getSubcontractorById(savedSubcontractorId);
				SubcontractorDto savedSubcontractorDto = dtoMapper.subcontractorToDto(savedSubcontractor);
				return new ResponseEntity<>(savedSubcontractorDto, HttpStatus.CREATED);
			}
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
