package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.entity.ModelTracking;
import com.example.demo.entity.GstStatusModelSubcontractor;

@Mapper
public interface ModelTrackingMapper {
// subcontractor 
	@Insert("INSERT INTO gst_status_model_subcontractor (status_ms_fk_subcontractor_id, status_ms_fk_message_model_id, status_ms_fk_status_id, status_ms_sent_date, status_ms_validation_date) "
			+ "VALUES (#{statusMsFkSubcontractorId}, #{statusMsFkMessageModelId}, #{statusMsFkStatusId}, #{statusMsSentDate}, #{statusMsValidationDate})")
	@Options(useGeneratedKeys = true, keyProperty = "statusMsId", keyColumn = "status_ms_id")
	@Result(property = "statusMsId", column = "status_ms_id")
	@Result(property = "statusMsFkSubcontractorId", column = "status_ms_fk_subcontractor_id")
	@Result(property = "statusMsFkMessageModelId", column = "status_ms_fk_message_model_id")
	@Result(property = "statusMsFkStatusId", column = "status_ms_fk_status_id")
	@Result(property = "statusMsSentDate", column = "status_ms_sent_date")
	@Result(property = "statusMsValidationDate", column = "status_ms_validation_date")
	int insertGstStatusModelSubcontractor(GstStatusModelSubcontractor gstStatusModelSubcontractor);
	
	
	@Update("UPDATE gst_status_model_subcontractor " +
	        "SET status_ms_fk_subcontractor_id = #{statusMsFkSubcontractorId}, " +
	        "    status_ms_fk_message_model_id = #{statusMsFkMessageModelId}, " +
	        "    status_ms_fk_status_id = #{statusMsFkStatusId}, " +
	        "    status_ms_sent_date = #{statusMsSentDate}, " +
	        "    status_ms_validation_date = #{statusMsValidationDate} " +
	        "WHERE status_ms_fk_subcontractor_id = #{statusMsFkSubcontractorId} " +
		    "AND status_ms_fk_message_model_id = #{statusMsFkMessageModelId}")
	@Result(property = "statusMsId", column = "status_ms_id")
	@Result(property = "statusMsFkSubcontractorId", column = "status_ms_fk_subcontractor_id")
	@Result(property = "statusMsFkMessageModelId", column = "status_ms_fk_message_model_id")
	@Result(property = "statusMsFkStatusId", column = "status_ms_fk_status_id")
	@Result(property = "statusMsSentDate", column = "status_ms_sent_date")
	@Result(property = "statusMsValidationDate", column = "status_ms_validation_date")
	int updateGstStatusModelSubcontractor(GstStatusModelSubcontractor gstStatusModelSubcontractor);
	
	@Select("SELECT " +
	        "ms.status_ms_id AS statusMsId, " +
	        "ms.status_ms_fk_subcontractor_id AS statusMsFkSubcontractorId, " +
	        "ms.status_ms_fk_message_model_id AS statusMsFkMessageModelId, " +
	        "ms.status_ms_fk_status_id AS statusMsFkStatusId, " +
	        "ms.status_ms_sent_date AS statusMsSentDate, " +
	        "ms.status_ms_validation_date AS statusMsValidationDate " +
	        "FROM gst_status_model_subcontractor ms " +
	        "WHERE ms.status_ms_fk_subcontractor_id = #{subcontractorId}")
	List<GstStatusModelSubcontractorDTO> findSubcontractorReminderInfo(int subcontractorId);
	
	@Select("SELECT " +
			"ms.status_ms_id AS statusMsId, " +
	        "ms.status_ms_fk_subcontractor_id AS statusMsFkSubcontractorId, " +
	        "ms.status_ms_fk_message_model_id AS statusMsFkMessageModelId, " +
	        "ms.status_ms_fk_status_id AS statusMsFkStatusId, " +
	        "ms.status_ms_sent_date AS statusMsSentDate, " +
	        "ms.status_ms_validation_date AS statusMsValidationDate " +
	        "FROM gst_status_model_subcontractor ms " +
	        "WHERE ms.status_ms_fk_subcontractor_id = #{subcontractorId} " +
			"AND status_ms_fk_message_model_id = #{mmId}")
	GstStatusModelSubcontractorDTO findAlertBySubcontractorIdAndMmId(int subcontractorId, int mmId);
	
// serviceProvider
	@Insert("INSERT INTO gst_status_model_service_provider (status_msp_fk_service_provider_id, status_msp_fk_message_model_id, status_msp_fk_status_id, status_msp_sent_date, status_msp_validation_date) "
			+ "VALUES (#{statusMspFkServiceProviderId}, #{statusMspFkMessageModelId}, #{statusMspFkStatusId}, #{statusMspSentDate}, #{statusMspValidationDate})")
	@Options(useGeneratedKeys = true, keyProperty = "statusMspId", keyColumn = "status_msp_id")
	@Result(property = "statusMspId", column = "status_msp_id")
	@Result(property = "statusMspFkServiceProviderId", column = "status_msp_fk_service_provider_id")
	@Result(property = "statusMspFkMessageModelId", column = "status_msp_fk_message_model_id")
	@Result(property = "statusMspFkStatusId", column = "status_msp_fk_status_id")
	@Result(property = "statusMspSentDate", column = "status_msp_sent_date")
	@Result(property = "statusMspValidationDate", column = "status_msp_validation_date")
	int insertGstStatusModelServiceProvider(ModelTracking gstStatusModelServiceProvider);
	
	@Update("UPDATE gst_status_model_service_provider " +
	        "SET status_msp_fk_service_provider_id = #{statusMspFkServiceProviderId}, " +
	        "    status_msp_fk_message_model_id = #{statusMspFkMessageModelId}, " +
	        "    status_msp_fk_status_id = #{statusMspFkStatusId}, " +
	        "    status_msp_sent_date = #{statusMspSentDate}, " +
	        "    status_msp_validation_date = #{statusMspValidationDate} " +
	        "WHERE status_msp_fk_service_provider_id = #{statusMspFkServiceProviderId} " +
	        "AND status_msp_fk_message_model_id = #{statusMspFkMessageModelId}")
	@Result(property = "statusMspId", column = "status_msp_id")
	@Result(property = "statusMspFkServiceProviderId", column = "status_msp_fk_service_provider_id")
	@Result(property = "statusMspFkMessageModelId", column = "status_msp_fk_message_model_id")
	@Result(property = "statusMspFkStatusId", column = "status_msp_fk_status_id")
	@Result(property = "statusMspSentDate", column = "status_msp_sent_date")
	@Result(property = "statusMspValidationDate", column = "status_msp_validation_date")
	int updateGstStatusModelServiceProvider(ModelTracking gstStatusModelServiceProvider);

	@Select("SELECT " +
            "msp.status_msp_id AS statusMspId, " +
            "msp.status_msp_fk_service_provider_id AS statusMspFkServiceProviderId, " +
            "msp.status_msp_fk_message_model_id AS statusMspFkMessageModelId, " +
            "msp.status_msp_fk_status_id AS statusMspFkStatusId, " +
            "msp.status_msp_sent_date AS statusMspSentDate, " +
            "msp.status_msp_validation_date AS statusMspValidationDate " +
            "FROM gst_status_model_service_provider msp " +
            "WHERE msp.status_msp_fk_service_provider_id = #{serviceProviderId}")
    List<ModelTrackingDTO> findServiceProviderReminderInfo(int serviceProviderId);
	
	@Select("SELECT " +
            "msp.status_msp_id AS statusMspId, " +
            "msp.status_msp_fk_service_provider_id AS statusMspFkServiceProviderId, " +
            "msp.status_msp_fk_message_model_id AS statusMspFkMessageModelId, " +
            "msp.status_msp_fk_status_id AS statusMspFkStatusId, " +
            "msp.status_msp_sent_date AS statusMspSentDate, " +
            "msp.status_msp_validation_date AS statusMspValidationDate " +
            "FROM gst_status_model_service_provider msp " +
            "WHERE msp.status_msp_fk_service_provider_id = #{serviceProviderId} " +
			"AND status_msp_fk_message_model_id = #{mmId}")
    ModelTrackingDTO findAlertByServiceProviderIdAndMmId(int serviceProviderId, int mmId);
}
