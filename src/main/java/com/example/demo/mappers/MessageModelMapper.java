package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.MessageModel;

@Mapper
public interface MessageModelMapper {

//	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as status_stId, st.st_name as status_stName, st.st_description as status_stDescription "
//			+ "FROM subcontractor s " + "INNER JOIN status st ON s.s_fk_status_id = st.st_id "
//			+ "WHERE st.st_id= ${statusId} "
//			+ "ORDER BY ${nameColonne}  ${sorting} LIMIT  #{offset}  OFFSET #{pageSize} ")
	@Select("SELECT  gmm.mm_id, gmm.mm_type, gmm.mm_subject, gmm.mm_body, st.st_Id as status_stId,st.st_name as status_stName, st.st_description as status_stDescription "
            +"FROM gst_message_model gmm "
            +"INNER JOIN status st ON mm_fk_status_id = st.st_Id "
            +"WHERE mm_fk_status_id = ${statusId}")
	@Result(property = "mmId", column = "mm_id")
	@Result(property = "mmType", column = "mm_type")
	@Result(property = "mmSubject", column = "mm_subject")
	@Result(property = "mmBody", column = "mm_body")
	@Result(property = "mmCreationDate", column = "mm_creationDate")
	@Result(property = "mmLastUpdateDate", column = "mm_lastUpdateDate")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	List<MessageModel>getAllMessageModelWhitStatus(@Param("statusId") Integer statusId);
}
