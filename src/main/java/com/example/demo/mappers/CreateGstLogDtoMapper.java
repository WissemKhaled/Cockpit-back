package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.entity.GstLog;

@Component
public class CreateGstLogDtoMapper {
	public CreateGstLogDTO toDto(GstLog gstLog) {
		return new CreateGstLogDTO(gstLog.getLogId(), gstLog.getLogType(), gstLog.getLogEmail(), gstLog.getLogValue(),
				gstLog.getLogCreationDate());
	}

	public GstLog toGstLog(CreateGstLogDTO createGstLogDTO) {
		return new GstLog(createGstLogDTO.getLogId(), createGstLogDTO.getLogType(), createGstLogDTO.getLogEmail(),
				createGstLogDTO.getLogValue(), createGstLogDTO.getLogCreationDate());
	}
}
