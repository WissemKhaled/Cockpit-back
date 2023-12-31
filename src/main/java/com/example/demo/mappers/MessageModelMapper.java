package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {

/*
	@Select("SELECT  gmm.mm_id, gmm.mm_category, gmm.mm_type, gmm.mm_subject, gmm.mm_body, st.st_Id as status_stId,st.st_name as status_stName, st.st_description as status_stDescription "
            +"FROM gst_message_model gmm "
            +"INNER JOIN status st ON mm_fk_status_id = st.st_Id "
            +"WHERE mm_fk_status_id = ${statusId}")
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
	List<MessageModel>getAllMessageModelWhitStatus(@Param("statusId") Integer statusId);*/
	
	
	
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
//	@Result(property = "spId", column = "sp_id")
	@Result(property = "status.stId", column = "status_stId")
	List<MessageModel>getMessageModelsAndStatusByServiceProviderId(@Param("serviceProviderId") Integer serviceProviderId);
}
