package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {
	
	@Select("SELECT DISTINCT " +
			"gmm.*, " +
	        "category.cat_name AS cat_name " +
	        "FROM schema_dev.gst_message_model gmm " +
	        "INNER JOIN schema_dev.gst_category category ON category.cat_id = gmm.mm_fk_category_id " +
	        "ORDER BY gmm.mm_id ASC")
	@Results({
	        @Result(property = "mmId", column = "mm_id"),
	        @Result(property = "mmLink", column = "mm_link"),
	        @Result(property = "mmSubject", column = "mm_subject"),
	        @Result(property = "mmBody", column = "mm_body"),
	        @Result(property = "mmHasEmail", column = "mm_has_email"),
	        @Result(property = "mmCreationDate", column = "mm_creation_date"),
	        @Result(property = "mmLastUpdateDate", column = "mm_last_update_date"),
	        @Result(property = "category.catId", column = "mm_fk_category_id"),
	        @Result(property = "category.catName", column = "cat_name")
	})
	List<MessageModel> getAllMessageModels();
	
	@Select("SELECT DISTINCT " +
	        "gmm.*, " +
	        "category.cat_name AS cat_name " +
	        "FROM schema_dev.gst_message_model gmm " +
	        "INNER JOIN schema_dev.gst_model_tracking gmt ON gmt.mt_fk_message_model_id = gmm.mm_id " +
	        "INNER JOIN schema_dev.gst_category category ON category.cat_id = gmm.mm_fk_category_id " +
	        "INNER JOIN schema_dev.gst_contract gc ON gmt.mt_fk_contract_id = gc.c_id " +
	        "WHERE gc.c_fk_subcontractor_id = #{subContractorId} " +
	        "AND gmm.mm_fk_category_id != 1 " +
	        "ORDER BY gmm.mm_id ASC")
	@Results({
	        @Result(property = "mmId", column = "mm_id"),
	        @Result(property = "mmLink", column = "mm_link"),
	        @Result(property = "mmSubject", column = "mm_subject"),
	        @Result(property = "mmBody", column = "mm_body"),
	        @Result(property = "mmHasEmail", column = "mm_has_email"),
	        @Result(property = "mmCreationDate", column = "mm_creation_date"),
	        @Result(property = "mmLastUpdateDate", column = "mm_last_update_date"),
	        @Result(property = "category.catId", column = "mm_fk_category_id"),
	        @Result(property = "category.catName", column = "cat_name")
	})
	List<MessageModel> getAllMessageModelBySubcontractorId(
	        @Param("subContractorId") Integer subContractorId);
	
	@Select("SELECT DISTINCT " +
			"gmm.*, " +
	        "category.cat_name AS cat_name " +
	        "FROM schema_dev.gst_message_model gmm " +
	        "INNER JOIN schema_dev.gst_model_tracking gmt ON gmt.mt_fk_message_model_id = gmm.mm_id " +
	        "INNER JOIN schema_dev.gst_category category ON category.cat_id = gmm.mm_fk_category_id " +
	        "INNER JOIN schema_dev.gst_contract gc ON gmt.mt_fk_contract_id = gc.c_id " +
	        "WHERE gc.c_fk_service_provider_id = #{serviceProviderId} " +
	        "AND gmm.mm_fk_category_id = 1 " +
	        "ORDER BY gmm.mm_id ASC")
	@Results({
	        @Result(property = "mmId", column = "mm_id"),
	        @Result(property = "mmLink", column = "mm_link"),
	        @Result(property = "mmSubject", column = "mm_subject"),
	        @Result(property = "mmBody", column = "mm_body"),
	        @Result(property = "mmHasEmail", column = "mm_has_email"),
	        @Result(property = "mmCreationDate", column = "mm_creation_date"),
	        @Result(property = "mmLastUpdateDate", column = "mm_last_update_date"),
	        @Result(property = "category.catId", column = "mm_fk_category_id"),
	        @Result(property = "category.catName", column = "cat_name")
	})
	List<MessageModel> getAllMessageModelByServiceProviderId(
	        @Param("serviceProviderId") Integer serviceProviderId);
}
