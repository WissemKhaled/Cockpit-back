package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.entity.ModelTracking;

@Mapper
public interface ModelTrackingMapper {
	
	@Insert("INSERT INTO schema_dev.gst_model_tracking (mt_send_date, mt_validation_date, mt_fk_contract_id, mt_fk_message_model_id, mt_fk_status_id, mt_fk_category_id) "
	        + "VALUES (#{mtSendDate}, #{mtValidationDate}, #{mtFkContractId}, #{mtFkMessageModelId}, #{mtFkStatusId}, #{mtFkCategoryId})")
	@Options(useGeneratedKeys = true, keyProperty = "mtId", keyColumn = "mt_id")
	@Result(property = "mtId", column = "mt_id")
	@Result(property = "mtSendDate", column = "mt_send_date")
	@Result(property = "mtValidationDate", column = "mt_validation_date")
	@Result(property = "mtFkContractId", column = "mt_fk_contract_id")
	@Result(property = "mtFkMessageModelId", column = "mt_fk_message_model_id")
	@Result(property = "mtFkStatusId", column = "mt_fk_status_id")
	@Result(property = "mtFkCategoryId", column = "mt_fk_category_id")
	int insertGstModelTracking(ModelTracking modelTracking);

	
	
	@Update("UPDATE schema_dev.gst_model_tracking " +
	        "SET mt_send_date = #{mtSendDate}, " +
	        "    mt_validation_date = #{mtValidationDate}, " +
	        "    mt_fk_contract_id = #{mtFkContractId}, " +
	        "    mt_fk_message_model_id = #{mtFkMessageModelId}, " +
	        "    mt_fk_status_id = #{mtFkStatusId}, " +
	        "    mt_fk_category_id = #{mtFkCategoryId} " +
			"WHERE mt_fk_contract_id = #{mtFkContractId} " +
		    "AND mt_fk_message_model_id = #{mtFkMessageModelId}")
	@Result(property = "mtId", column = "mt_id")
	@Result(property = "mtFkContractId", column = "mt_fk_contract_id")
	@Result(property = "mtFkMessageModelId", column = "mt_fk_message_model_id")
	@Result(property = "mtFkStatusId", column = "mt_fk_status_id")
	@Result(property = "mtFkCategoryId", column = "mt_fk_category_id")
	@Result(property = "mtSendDate", column = "mt_send_date")
	@Result(property = "mtValidationDate", column = "mt_validation_date")
	int updateModelTracking(ModelTracking modelTracking);

	
	@Select("SELECT " +
	        "mt.mt_id AS mtId, " +
	        "mt.mt_send_date AS mtSendDate, " +
	        "mt.mt_validation_date AS mtValidationDate, " +
	        "mt.mt_fk_contract_id AS mtFkContractId, " +
	        "mt.mt_fk_message_model_id AS mtFkMessageModelId, " +
	        "mt.mt_fk_status_id AS mtFkStatusId, " +
	        "mt.mt_fk_category_id AS mtFkCategoryId " +
	        "FROM schema_dev.gst_model_tracking mt " +
	        "WHERE mt.mt_fk_contract_id = #{contractId}")
	List<ModelTrackingDTO> findModelTrackingInfo(int contractId);

	
	@Select("SELECT " +
	        "mt.mt_id AS mtId, " +
	        "mt.mt_send_date AS mtSendDate, " +
	        "mt.mt_validation_date AS mtValidationDate, " +
	        "mt.mt_fk_contract_id AS mtFkContractId, " +
	        "mt.mt_fk_message_model_id AS mtFkMessageModelId, " +
	        "mt.mt_fk_status_id AS mtFkStatusId, " +
	        "mt.mt_fk_category_id AS mtFkCategoryId " +
	        "FROM schema_dev.gst_model_tracking mt " +
	        "WHERE mt_fk_contract_id = #{contractId} " +
			"AND mt_fk_message_model_id = #{mmId}")
    ModelTrackingDTO findModelTrackingInfoByContractIdAndMmId(int contractId, int mmId);
}
