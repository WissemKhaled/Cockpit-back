package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {


	@Select("SELECT  gmm.mm_id, gmm.mm_category, gmm.mm_type, gmm.mm_subject, gmm.mm_body, st.st_Id as status_stId,st.st_name as status_stName, st.st_description as status_stDescription "
            +"FROM gst_message_model gmm "
            +"INNER JOIN status st ON mm_fk_status_id = st.st_Id "
            +"WHERE mm_fk_status_id = ${statusId}")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creationDate")
	@Result(property = "mmLastUpdateDate", column = "mm_lastUpdateDate")
	@Result(property = "mmCategory", column = "mm_category")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	List<MessageModel> getAllMessageModelByStatusId(@Param("statusId") Integer statusId);

	@Select("SELECT  gmm.mm_id, gmm.mm_category, gmm.mm_type, gmm.mm_subject, gmm.mm_body, st.st_Id as status_stId,st.st_name as status_stName, st.st_description as status_stDescription "
			+"FROM gst_message_model")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmCategory", column = "mm_category")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creationDate")
	@Result(property = "mmLastUpdateDate", column = "mm_lastUpdateDate")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	List<MessageModel>getAllMessageModels();
	
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
	List<MessageModel> getMessageModelsAndStatusBySubcontractorCategory(@Param("subcontractorId") Integer subcontractorId);


	@Select("SELECT DISTINCT " +
			"gmm.mm_id, " +
			"gmm.mm_link, " +
			"gmm.mm_has_email AS mmHasEmail, " +
			"gsc.s_id, " +
			"gsc.s_name, " +
			"gsc.s_email, " +
			"gsc.s_fk_status_id as s_fk_status_id, " +
			"gsp.sp_id, " +
			"gsp.sp_name, " +
			"gsp.sp_email, " +
			"gsp.sp_fk_status_id as sp_fk_status_id, " +
			"gc.cat_name AS cat_name, " +
			"gmm.mm_fk_category_id, " +
			"gmm.mm_link, " +
			"gmm.mm_subject, " +
			"gmm.mm_body, " +
			"gmm.mm_last_update, " +
			"gmm.mm_creation_date, " +
			"gmt.mt_send_date AS send_date, " +
			"gmt.mt_validation_date AS validation_date, " +
			"st.st_id AS statusId, " +
			"st.st_name AS status_stName " +
			"FROM gst_model_tracking gmt " +
			"LEFT JOIN gst_message_model gmm ON gmm.mm_id = gmt.mt_fk_message_model_id " +
			"LEFT JOIN gst_category gc ON gmt.mt_fk_category_id = gc.cat_id " +
			"LEFT JOIN gst_status st ON gmt.mt_fk_status_id = st.st_id " +
			"LEFT JOIN gst_subcontractor gsc ON st.st_id = gsc.s_fk_status_id " +
			"LEFT JOIN gst_service_provider gsp ON st.st_id = gsp.sp_fk_status_id " +
			"WHERE " +
			"(gsc.s_id IS NULL OR (gsc.s_id = #{subContractorId} AND gc.cat_name = 'SC')) " +
			"OR (gsp.sp_id IS NULL OR (gsp.sp_id = #{serviceProviderId} AND gc.cat_name = 'SP')) " +
			"OR (s_fk_status_id IS NULL OR (s_fk_status_id = #{subContractorStatusId} AND gc.cat_name = 'SC')) " +
			"OR (sp_fk_status_id IS NULL OR (sp_fk_status_id = #{serviceProviderStatusId} AND gc.cat_name = 'SP'))")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmLink", column = "mm_link")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmHasEmail", column = "mmHasEmail")
	@Result(property = "mmCreationDate", column = "mm_creationDate")
	@Result(property = "mmLastUpdateDate", column = "mm_last_update")
	@Result(property = "category.catId", column = "mm_fk_category_id")
	@Result(property = "category.catName", column = "cat_name")
	List<MessageModel> getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId( @Param("subContractorId") Integer subContractorId,  @Param("serviceProviderId") Integer serviceProviderId, @Param("subContractorStatusId") Integer subContractorStatusId, @Param("serviceProviderStatusId") Integer serviceProviderStatusId);



}