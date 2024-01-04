package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {


	
	@Select("SELECT  gmm.mm_id, smsp.status_msp_id, gmm.mm_category, smsp.status_msp_fk_service_provider_id, smsp.status_msp_fk_status_id, "
			+ "st.st_id as status_stId, st.st_name as status_stName, gmm.mm_type, gmm.mm_subject, gmm.mm_body, gmm.mm_creation_date, gmm.mm_last_update "
            +"FROM gst_status_model_service_provider smsp "
            +"JOIN service_provider sp ON sp.sp_id = smsp.status_msp_fk_service_provider_id "
            +"JOIN status st ON st.st_id = smsp.status_msp_fk_status_id "
            +"JOIN gst_message_model gmm ON gmm.mm_id = smsp.status_msp_fk_message_model_id "
            +"WHERE smsp.status_msp_fk_service_provider_id = ${serviceProviderId} "
            +"AND gmm.mm_category = 'SP' "
            +"ORDER BY sp.sp_name")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "statusMspId", column = "status_msp_id")
	@Result(property = "mmCategory", column = "mm_category")
	@Result(property = "statusMspFkServiceProviderId", column = "status_msp_fk_service_provider_id")
	@Result(property = "statusMspFkStatusId", column = "status_msp_fk_status_id")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creation_date")
	@Result(property = "mmLastUpdateDate", column = "mm_last_update")
	@Result(property = "status.stId", column = "status_stId")
	List<MessageModel>getMessageModelsAndStatusByServiceProviderId(@Param("serviceProviderId") Integer serviceProviderId);


	List<MessageModel>getAllMessageModelWhitStatus(@Param("statusId") Integer statusId);

	@Select("SELECT DISTINCT mm.mm_id, mm.mm_category, mm.mm_type, mm.mm_subject, mm.mm_body, " +
			"mm.mm_creation_date, mm.mm_last_update, mm.mm_fk_status_id, st.st_name as StName, " +
			"sc.s_id, sc.s_name,st.st_id as statusId " +
			"FROM public.gst_message_model AS mm " +
			"JOIN public.status AS st ON st.st_id = mm.mm_fk_status_id " +
			"JOIN public.gst_status_model_subcontractor AS sms ON mm.mm_id = sms.status_ms_fk_message_model_id " +
			"JOIN public.subcontractor AS sc ON sms.status_ms_fk_subcontractor_id = sc.s_id " +
			"WHERE sc.s_id = #{subcontractorId} AND mm.mm_category = 'SC' " +
			"ORDER BY sc.s_name")
	@Results({
			@Result(property = "mmId", column = "mm_id"),
			@Result(property = "mmCategory", column = "mm_category"),
			@Result(property = "mmType", column = "mm_type"),
			@Result(property = "mmSubject", column = "mm_subject"),
			@Result(property = "mmBody", column = "mm_body"),
			@Result(property = "mmCreationDate", column = "mm_creation_date"),
			@Result(property = "mmLastUpdate", column = "mm_last_update"),
			@Result(property = "mmStatusId.stId", column = "statusId"),
			@Result(property = "mmStatusId.stName", column = "StName")
	})
	List<MessageModel> getMessageModelsAndStatusBySubcontractorCategoryAndId(@Param("subcontractorId") Integer subcontractorId);

}
