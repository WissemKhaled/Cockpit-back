package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StatusDto;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.StatusService;

@RestController
@RequestMapping("/status")
@CrossOrigin(origins = "http://localhost:4200")
public class StatusController {
	
	private final StatusService statusService;

	public StatusController(StatusService statusService) {
		this.statusService = statusService;
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
			return new ResponseEntity<>(statusService.getAllStatus(), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
