package com.example.demo.controller;

import java.net.http.HttpRequest;

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
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.service.SubcontractorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/subcontractor")
@AllArgsConstructor
public class SubcontractorController {
	
    private final SubcontractorService subcontractorService;
	private final SubcontractorDtoMapper dtoMapper;


    @GetMapping("/subcontractor/{id}")
    public Subcontractor getSubcontractor(@PathVariable int id) {
        return subcontractorService.getSubcontractorWithStatus(id);
    }

	@PostMapping("/add")
	public ResponseEntity<?> addSubcontractor(@RequestBody SubcontractorDto subcontractorDto) {
		try {			
			System.err.println("I'm here1");
			System.err.println(subcontractorDto.getStatus().getStId());
			System.err.println(dtoMapper.dtoToSubcontractor(subcontractorDto));
			int savedSubcontractorId = subcontractorService.saveSubcontractor(dtoMapper.dtoToSubcontractor(subcontractorDto));
			System.err.println("I'm here2");
			Subcontractor savedSubcontractor = subcontractorService.getSubcontractorById(savedSubcontractorId);
			System.err.println("I'm here3");
			SubcontractorDto savedSubcontractorDto = dtoMapper.subcontractorToDto(savedSubcontractor);
			System.err.println("I'm here4");
			subcontractorService.saveSubcontractor(savedSubcontractor);
			System.err.println("I'm here5");
			return ResponseEntity.ok(savedSubcontractorDto);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.I_AM_A_TEAPOT);
		}

	}

//	@PutMapping("/update/{id}")
//	public ResponseEntity<SubcontractorDto> updateSubcontractor(@PathVariable int id,
//			@RequestBody SubcontractorDto updatedSubcontractorDto) {
//		try {
////            updatedSubcontractor.setId(id); // Assure que l'ID est bien défini
//			Subcontractor existingSubcontractor = subcontractorService.getSubcontractorById(id);
//			if (existingSubcontractor == null) {
//				return ResponseEntity.notFound().build();
//			}
//			Subcontractor updatedSubcontractor = dtoMapper.dtoToSubcontractor(updatedSubcontractorDto);
//			subcontractorService.updateSubcontractor(updatedSubcontractor);
//			SubcontractorDto modifedSubcontractorDto = dtoMapper
//					.subcontractorToDto(subcontractorService.getSubcontractorById(updatedSubcontractor.getSId()));
//
//			return ResponseEntity.ok(modifedSubcontractorDto);
//
//		} catch (Exception e) {
//			throw new RuntimeException("Error accured");
//		}
//
//	}
}
