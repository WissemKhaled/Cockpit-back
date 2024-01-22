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
    
    /**
	 * Récupère la liste des status des modèles de message liés au sous-traitant.
	 *
	 * @param subcontractorId      Id du sous-traitant.
	 * @return La liste d'ID des status des modèles de message liés au sous-traitant.
	 */
	int updateSubcontractorStatus(@Param("subcontractorId") int subcontractorId);
}
