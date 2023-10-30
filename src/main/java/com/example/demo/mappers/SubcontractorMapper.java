package com.example.demo.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {

//	@Insert("INSERT INTO subcontractor (s_name, s_email, s_fk_status_id) "
//			+ "VALUES (#{sName}, #{sEmail}, #{status.stId})")
//	@Options(useGeneratedKeys = true, keyProperty = "sId", keyColumn = "s_id")
//	void insertSubcontractor(Subcontractor subcontractor);

//
//	@Update("UPDATE subcontractor SET s_name = #{sName}, s_email = #{sEmail}, status = #{status} WHERE s_id = #{sId}")
//	void updateSubcontractor(Subcontractor subcontractor);
//	

	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as 'status.stId', st.st_name as 'status.stName', st.st_description as 'status.stDescription' "
			+ "FROM subcontractor s JOIN status st " + "ON s.s_fk_status_id = st.st_id " + "WHERE s.s_id = #{sId}")
	Subcontractor getSubcontractorWithStatus(int sId);

	@Select("SELECT s.s_id, s.s_name, s.s_email, s.s_fk_status_id as 'status.stId', st.st_name as 'status.stName', st.st_description as 'status.stDescription' "
			+ "FROM subcontractor s " + "JOIN status st ON s.s_fk_status_id = st.st_id " + "WHERE s.s_id = #{sId}")
	Subcontractor findSubcontractorById(int sId);

}
