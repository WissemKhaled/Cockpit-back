package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.service.MessageModelService;
import com.example.demo.service.ModelTrackingService;

@RestController
@RequestMapping("/MessageModel")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageModelController {

	@Autowired
	private MessageModelService messageModelService;

	@Autowired
	private ModelTrackingService modelTrackingService;

	public MessageModelController(
		MessageModelService messageModelService,
		ModelTrackingService modelTrackingService
	) {
		this.messageModelService = messageModelService;
		this.modelTrackingService = modelTrackingService;
	}

	
	@GetMapping("/getAllMessagesBySubcontractorId")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelBySubcontractorId(
			@RequestParam(value = "subcontractorId") Integer subcontractorId,
			@RequestParam(value = "statusId") Integer statusId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {
		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelBySubcontractorId(subcontractorId);

			Page<MessageModel> page = new PageImpl<>(allMessages, pageable, allMessages.size());

			// appel de la méthode qui gère les relances
			modelTrackingService.checkRelaunch(statusId);

			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Récupère une liste paginée de MessageModels basée sur l'ID du prestataire de services donné.
	 *
	 * @param serviceProviderId L'ID du prestataire de services pour lequel les messages doivent être récupérés.
	 * @param contractId L'ID du contrat associé aux messages.
	 * @param statusId L'ID de statut pour filtrer les messages.
	 * @param pageable Objet Pageable pour définir les paramètres de pagination (numéro de page et taille).
	 * @return ResponseEntity contenant une page d'objets MessageModel. Renvoie une réponse OK avec la page de messages si réussi, ou une réponse NOT FOUND si aucun message n'est trouvé.
	 * @throws MessageModelNotFoundException si aucun MessageModel n'est trouvé pour le serviceProviderId donné.
	 */
	@GetMapping("/getAllMessagesByServiceProviderId")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelByServiceProviderId(
			@RequestParam(value = "serviceProviderId") Integer serviceProviderId,
			@RequestParam(value = "statusId") Integer statusId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {
		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelByServiceProviderId(serviceProviderId);

			Page<MessageModel> page = new PageImpl<>(allMessages, pageable, allMessages.size());

			// appel de la méthode qui gère les relances
			modelTrackingService.checkRelaunch(statusId);

			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}


	@GetMapping("/getAllMessagesByContractId")
	public ResponseEntity<Page<MessageModel>> getAllMessageModelByContractId(
			@RequestParam(value = "contractId") Integer contractId,
			@PageableDefault(page = 0, size = 6) Pageable pageable) {
		try {
			List<MessageModel> allMessages = messageModelService.getAllMessageModelByContractId(contractId);

			Page<MessageModel> page = new PageImpl<>(allMessages, pageable, allMessages.size());

			return ResponseEntity.ok(page);

		} catch (MessageModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
