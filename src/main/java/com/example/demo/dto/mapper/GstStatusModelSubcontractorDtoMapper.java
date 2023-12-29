package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.entity.GstStatusModelSubcontractor;

@Component
public class GstStatusModelSubcontractorDtoMapper {
	public GstStatusModelSubcontractorDTO toDto(GstStatusModelSubcontractor gstStatusModelSubcontractor) {
		return new GstStatusModelSubcontractorDTO(gstStatusModelSubcontractor.getStatusMsId(), gstStatusModelSubcontractor.getStatusMsFkSubcontractorId(),
				gstStatusModelSubcontractor.getStatusMsFkMessageModelId(), gstStatusModelSubcontractor.getStatusMsFkStatusId(),
				gstStatusModelSubcontractor.getStatusMsSentDate(), gstStatusModelSubcontractor.getStatusMsValidationDate());
	}
	
	public GstStatusModelSubcontractor toGstStatusModelSubcontractor(GstStatusModelSubcontractorDTO gstStatusModelSubcontractorDTO) {
		return new GstStatusModelSubcontractor(gstStatusModelSubcontractorDTO.getStatusMsId(), gstStatusModelSubcontractorDTO.getStatusMsFkSubcontractorId(),
				gstStatusModelSubcontractorDTO.getStatusMsFkMessageModelId(), gstStatusModelSubcontractorDTO.getStatusMsFkStatusId(),
				gstStatusModelSubcontractorDTO.getStatusMsSentDate(), gstStatusModelSubcontractorDTO.getStatusMsValidationDate());
	}
}
