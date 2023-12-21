package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.GstStatusModelServiceProviderDTO;
import com.example.demo.entity.GstStatusModelServiceProvider;

@Component
public class GstStatusModelServiceProviderDtoMapper {
	public GstStatusModelServiceProviderDTO toDto(GstStatusModelServiceProvider gstStatusModelServiceProvider) {
		return new GstStatusModelServiceProviderDTO(gstStatusModelServiceProvider.getStatusMspId(), gstStatusModelServiceProvider.getStatusMspFkServiceProviderId(),
				gstStatusModelServiceProvider.getStatusMspFkMessageModelId(), gstStatusModelServiceProvider.getStatusMspFkStatusId());
	}
	
	public GstStatusModelServiceProvider toGstStatusModelSubcontractor(GstStatusModelServiceProviderDTO gstStatusModelServiceProviderDTO) {
		return new GstStatusModelServiceProvider(gstStatusModelServiceProviderDTO.getStatusMspId(), gstStatusModelServiceProviderDTO.getStatusMspFkServiceProviderId(),
				gstStatusModelServiceProviderDTO.getStatusMspFkMessageModelId(), gstStatusModelServiceProviderDTO.getStatusMspFkStatusId());
	}
}
