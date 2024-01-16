package com.example.demo.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.entity.ModelTracking;

@Mapper
public interface ModelTrackingMapper {
	
	int insertGstModelTracking(ModelTracking modelTracking);

    int updateModelTracking(ModelTracking modelTracking);

    List<ModelTrackingDTO> findAllModelTrackingInfo();

    List<ModelTrackingDTO> getModelTrackingInfoByContractId(int contractId);

    List<ModelTrackingDTO> findModelTrackingInfoByMmId(int mmId);

    ModelTrackingDTO findModelTrackingInfoByContractIdAndMmId(@Param("contractId") int contractId, @Param("mmId") int mmId);
}
