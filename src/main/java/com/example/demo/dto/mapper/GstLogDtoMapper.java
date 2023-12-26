package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.GstLogDTO;
import com.example.demo.entity.GstLog;

@Component
public class GstLogDtoMapper {
	public GstLogDTO toDto(GstLog gstLog) {
		return new GstLogDTO(gstLog.getLogId(), gstLog.getLogType(), gstLog.getLogEmail(), gstLog.getLogValue(),
				gstLog.getLogCreationDate());
	}

	public GstLog toGstLog(GstLogDTO gstLogDTO) {
		return new GstLog(gstLogDTO.getLogId(), gstLogDTO.getLogType(), gstLogDTO.getLogEmail(),
				gstLogDTO.getLogValue(), gstLogDTO.getLogCreationDate());
	}
}
