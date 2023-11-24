package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateMessageModelDTO;
import com.example.demo.dto.MessageModelDTO;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.CreateMessageModelDtoMapper;
import com.example.demo.mappers.MessageModelDtoMapper;
import com.example.demo.mappers.MessageModelMapper;

import lombok.extern.java.Log;

@Log
@Service
public class MessageModelServiceImpl implements MessageModelService {
	@Autowired
	MessageModelMapper messageModelMapper;
	
	@Autowired
	private CreateMessageModelDtoMapper createMessageModelDtoMapper;
	
	@Autowired 
	private MessageModelDtoMapper messageModelDtoMapper;
	
	/**
	 * Méthode qui enregistre un modèle de message en bdd
	 */
	@Override
    public String saveMessageModel(CreateMessageModelDTO createMessageModelDto) throws GeneralException {
        try {
            if (createMessageModelDto != null) {
                MessageModel messageModel = createMessageModelDtoMapper.toMessageModel(createMessageModelDto);
                messageModel.setMmCreationDate(LocalDateTime.now());

                int isMessageModelInserted = messageModelMapper.insertMessageModel(messageModel);

                if (isMessageModelInserted == 0) {
                    log.severe("Échec de l'insertion du modèle de message dans la base de données");
                    throw new GeneralException("Échec de l'insertion du modèle de message dans la base de données");
                }
                log.info("Modèle de message créé avec succès");
                return "Modèle de message créé avec succès";
            } else {
            	log.severe("Le paramètre createMessageModelDto ne peut pas être nul");
                throw new IllegalArgumentException("Le paramètre createMessageModelDto ne peut pas être nul");
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new GeneralException("Erreur lors de la création du modèle de message");
        }
    }
	
	@Override
	public MessageModelDTO getMessageModelById(int mmId) throws NotFoundException {
	    // Retrieve the MessageModel by its ID
	    MessageModel messageModel = messageModelMapper.getMessageModelById(mmId);

	    if (messageModel != null) {
	        // Convert the MessageModel to MessageModelDTO
	        return messageModelDtoMapper.toDto(messageModel);
	    } else {
	        throw new NotFoundException("Aucun modèle de message trouvé pour l'ID : " + mmId);
	    }
	}


	/**
	 * Méthode qui récupère une liste des modèles de message par type
	 */
	 @Override
	 public List<MessageModelDTO> getMessageModelByType(String mmType) throws NotFoundException {
	     if (mmType != null && !mmType.isEmpty()) {
	         List<MessageModel> messageModels = messageModelMapper.getMessageModelByType(mmType);
	         if (messageModels != null && !messageModels.isEmpty()) {
	             List<MessageModelDTO> messageModelDTOs = messageModels.stream()
	                     .map(messageModelDtoMapper::toDto)
	                     .collect(Collectors.toList());
	             return messageModelDTOs;
	         } else {
	             throw new NotFoundException("Aucun modèle de message trouvé pour le type : " + mmType);
	         }
	     } else {
	         throw new IllegalArgumentException("mmType ne peut pas être vide ou nul");
	     }
	 }
	 
	 /**
	  * Méthode qui édite un un modèle de message par id
	 */
	 @Override
	 public String editMessageModel(int mmId, MessageModel messageModel) throws GeneralException {
	     try {
	         log.info("Retrieved MessageModel: " + messageModel);
	         
	         messageModel.setMmType(messageModel.getMmType());
	         messageModel.setMmSubject(messageModel.getMmSubject());
	         messageModel.setMmBody(messageModel.getMmBody());
	         messageModel.setMmCreationDate(messageModel.getMmCreationDate());
	         messageModel.setMmLastUpdate(LocalDateTime.now());
	         
	         int isMessageModelUpdated = messageModelMapper.updateMessageModel(messageModel);

	         if (isMessageModelUpdated == 0) {
	             log.severe("Échec de la mise à jour du modèle de message dans la base de données");
	             throw new GeneralException("Échec de la mise à jour du modèle de message dans la base de données");
	         }

	         log.info("Modèle de message édité avec succès");
	         return "Modèle de message édité avec succès";
	     } catch (Exception e) {
	         log.severe("Erreur lors de l'édition du modèle de message : " + e.getMessage());
	         throw new GeneralException("Erreur lors de l'édition du modèle de message");
	     }
	 }

}
