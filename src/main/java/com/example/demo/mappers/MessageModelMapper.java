package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {


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
