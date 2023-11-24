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
	
	@Override
	public String saveGstLog(CreateGstLogDTO createGstLogDTO) throws GeneralException {
		try {
            if (createGstLogDTO != null) {
                GstLog gstLog = createGstLogDtoMapper.toGstLog(createGstLogDTO);
                gstLog.setLogCreationDate(LocalDateTime.now());

                int isMessageModelInserted = gstLogMapper.insertLog(gstLog);

                if (isMessageModelInserted == 0) {
                    log.severe("Failed to insert gst log into the database");
                    throw new GeneralException("Failed to insert gst log into the database");
                }
                log.info("gst log created successfully");
                return "gst log created successfully";
            } else {
                log.severe("Parameter createGstLogDTO cannot be null");
                throw new IllegalArgumentException("Parameter createGstLogDTO cannot be null");
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new GeneralException("Error during the creation of the message model");
        }
	}

	@Override
	public GstLogDTO getGstLogByValue(String logValue) throws NotFoundException {
		if (logValue != null && !logValue.isEmpty()) {
			GstLog gstLog = gstLogMapper.getLogByValue(logValue);
	         if (gstLog != null) {
	             GstLogDTO messageModelDTO = gstLogDtoMapper.toDto(gstLog);
	             return messageModelDTO;
	         } else {
	             throw new NotFoundException("No gst log found for type: " + logValue);
	         }
	     } else {
	         throw new IllegalArgumentException("logValue cannot be empty or null");
	     }
	}

}
