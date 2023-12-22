package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.GstStatusModelServiceProvider;
import com.example.demo.entity.GstStatusModelSubcontractor;

@Mapper
public interface EmailReminderMapper {
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
	        "WHERE status_ms_fk_subcontractor_id = #{statusMsFkSubcontractorId}")
	@Result(property = "statusMsId", column = "status_ms_id")
	@Result(property = "statusMsFkSubcontractorId", column = "status_ms_fk_subcontractor_id")
	@Result(property = "statusMsFkMessageModelId", column = "status_ms_fk_message_model_id")
	@Result(property = "statusMsFkStatusId", column = "status_ms_fk_status_id")
	@Result(property = "statusMsSentDate", column = "status_ms_sent_date")
	@Result(property = "statusMsValidationDate", column = "status_ms_validation_date")
	int updateGstStatusModelSubcontractor(GstStatusModelSubcontractor gstStatusModelSubcontractor);
	
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
	int insertGstStatusModelServiceProvider(GstStatusModelServiceProvider gstStatusModelServiceProvider);
	
	@Update("UPDATE gst_status_model_service_provider " +
	        "SET status_msp_fk_service_provider_id = #{statusMspFkServiceProviderId}, " +
	        "    status_msp_fk_message_model_id = #{statusMspFkMessageModelId}, " +
	        "    status_msp_fk_status_id = #{statusMspFkStatusId}, " +
	        "    status_msp_sent_date = #{statusMspSentDate}, " +
	        "    status_msp_validation_date = #{statusMspValidationDate} " +
	        "WHERE status_msp_fk_service_provider_id = #{statusMspFkServiceProviderId}")
	@Result(property = "statusMspId", column = "status_msp_id")
	@Result(property = "statusMspFkServiceProviderId", column = "status_msp_fk_service_provider_id")
	@Result(property = "statusMspFkMessageModelId", column = "status_msp_fk_message_model_id")
	@Result(property = "statusMspFkStatusId", column = "status_msp_fk_status_id")
	@Result(property = "statusMspSentDate", column = "status_msp_sent_date")
	@Result(property = "statusMspValidationDate", column = "status_msp_validation_date")
	int updateGstStatusModelServiceProvider(GstStatusModelServiceProvider gstStatusModelServiceProvider);
}
