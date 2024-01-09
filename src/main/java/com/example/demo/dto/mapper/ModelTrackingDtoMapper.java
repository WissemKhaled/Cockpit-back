package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.entity.ModelTracking;

@Component
public class ModelTrackingDtoMapper {

    public ModelTrackingDTO toDto(ModelTracking modelTracking) {
        return new ModelTrackingDTO(modelTracking.getMtId(), modelTracking.getMtFkContractId(),
                modelTracking.getMtFkMessageModelId(), modelTracking.getMtFkStatusId(),
                modelTracking.getMtFkCategoryId(), modelTracking.getMtSendDate(),
                modelTracking.getMtValidationDate());
    }

    public ModelTracking toModelTracking(ModelTrackingDTO modelTrackingDTO) {
        return new ModelTracking(modelTrackingDTO.getMtId(), modelTrackingDTO.getMtFkContractId(),
                modelTrackingDTO.getMtFkMessageModelId(), modelTrackingDTO.getMtFkStatusId(),
                modelTrackingDTO.getMtFkCategoryId(), modelTrackingDTO.getMtSendDate(),
                modelTrackingDTO.getMtValidationDate());
    }
}