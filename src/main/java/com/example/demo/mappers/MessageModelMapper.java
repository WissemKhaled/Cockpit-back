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
	@Result(property = "mmCategory", column = "mm_category")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creationDate")
	@Result(property = "mmLastUpdateDate", column = "mm_lastUpdateDate")
	@Result(property = "mmStatusId.stId", column = "status_stId")
	@Result(property = "mmStatusId.stName", column = "status_stName")
	@Result(property = "mmStatusId.stDescription", column = "status_stDescription")
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
