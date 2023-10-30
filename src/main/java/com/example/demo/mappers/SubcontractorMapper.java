package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {

	@Insert("INSERT INTO subcontractor (s_name, s_email, s_fk_status_id) "
			+ "VALUES (#{sName}, #{sEmail}, #{status.stId})")
	@Options(useGeneratedKeys = true, keyProperty = "sId", keyColumn = "s_id")
	@Results({ @Result(property = "sId", column = "s_id"), @Result(property = "sName", column = "s_name"),
			@Result(property = "sEmail", column = "s_email"),
			@Result(property = "status.stId", column = "s_fk_status_id") })
	void insertSubcontractor(Subcontractor subcontractor);

	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as 'status.stId', st.st_name as 'status.stName', st.st_description as 'status.stDescription' "
			+ "FROM subcontractor s JOIN status st " + "ON s.s_fk_status_id = st.st_id " + "WHERE s.s_id = #{sId}")
	Subcontractor getSubcontractorWithStatus(int sId);

	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id AS status_stId, st.st_name AS status_stName, st.st_description AS status_stDescription "
			+ "FROM subcontractor s " + "JOIN status st ON s.s_fk_status_id = st.st_id " + "WHERE s.s_id = #{sId}")
	@Results({ @Result(property = "sId", column = "s_id"), @Result(property = "sName", column = "s_name"),
			@Result(property = "sEmail", column = "s_email"), @Result(property = "status.stId", column = "status_stId"),
			@Result(property = "status.stName", column = "status_stName"),
			@Result(property = "status.stDescription", column = "status_stDescription") })
	Subcontractor findSubcontractorById(int sId);

	@Update("UPDATE subcontractor " + "SET s_name = #{sName}, s_email = #{sEmail}, s_fk_status_id = #{status.stId} "
			+ "WHERE s_id = #{sId}")
	@Results({ @Result(property = "sId", column = "s_id"), @Result(property = "sName", column = "s_name"),
			@Result(property = "sEmail", column = "s_email"),
			@Result(property = "status.stId", column = "s_fk_status_id") })
	void updateSubcontractor(Subcontractor subcontractor);

}
