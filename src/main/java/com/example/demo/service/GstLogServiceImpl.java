package com.example.demo.service;

import java.time.LocalDateTime;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.entity.GstLog;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.CreateGstLogDtoMapper;
import com.example.demo.mappers.GstLogDtoMapper;
import com.example.demo.mappers.GstLogMapper;

import lombok.extern.java.Log;

@Log
@Service
public class GstLogServiceImpl implements GstLogService{
	@Autowired
	GstLogMapper gstLogMapper;
	
	@Autowired
	private CreateGstLogDtoMapper createGstLogDtoMapper;
	
	@Autowired 
	private GstLogDtoMapper gstLogDtoMapper;
	
	/**
	 * Méthode qui enregistre un log en bdd
	 */
	@Override
	public String saveGstLog(CreateGstLogDTO createGstLogDTO) throws GeneralException {
		try {
            if (createGstLogDTO != null) {
                GstLog gstLog = createGstLogDtoMapper.toGstLog(createGstLogDTO);
                gstLog.setLogCreationDate(LocalDateTime.now());

                int isMessageModelInserted = gstLogMapper.insertLog(gstLog);

                if (isMessageModelInserted == 0) {
                    log.severe("Échec de l'insertion du gst log dans la base de données");
                    throw new GeneralException("Échec de l'insertion du gst log dans la base de données");
                }
                log.info("gst log créé avec succès");
                return "gst log créé avec succès";
            } else {
                log.severe("Le paramètre createGstLogDTO ne peut être null");
                throw new IllegalArgumentException("Le paramètre createGstLogDTO ne peut être null");
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new GeneralException("Erreur lors de la création du Gst log");
        }
	}
	
	/**
	 * Méthode qui récupère un log par sa valeur (logValue)
	 */
	@Override
	public GstLogDTO getGstLogByValue(String logValue) throws NotFoundException {
		if (logValue != null && !logValue.isEmpty()) {
			GstLog gstLog = gstLogMapper.getLogByValue(logValue);
	         if (gstLog != null) {
	             GstLogDTO messageModelDTO = gstLogDtoMapper.toDto(gstLog);
	             return messageModelDTO;
	         } else {
	             throw new NotFoundException("Aucun gst log trouvé pour le type: " + logValue);
	         }
	     } else {
	         throw new IllegalArgumentException("logValue ne ^peut être vide ou null");
	     }
	}

}
