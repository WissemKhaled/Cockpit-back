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

	@Insert("INSERT INTO subcontractor (s_name, s_email, s_fk_status_id) VALUES (#{sName}, #{sEmail}, #{sFkStatusId})")
	@Options(useGeneratedKeys = true, keyProperty = "sId", keyColumn = "s_id")
	@Results({ @Result(property = "sId", column = "s_id"), @Result(property = "sName", column = "s_name"),
			@Result(property = "sEmail", column = "s_email"),
			@Result(property = "sFkStatusId", column = "s_fk_status_id"),

	})
	void insertSubcontractor(Subcontractor subcontractor);

	@Update("UPDATE subcontractor SET name = #{name}, email = #{email}, status = #{status} WHERE id = #{id}")
	void updateSubcontractor(Subcontractor subcontractor);

	@Select("SELECT * FROM subcontractor WHERE s_id = #{sId}")
    @Results({
        @Result(property = "sId", column = "s_id"),
        @Result(property = "sName", column = "s_name"),
        @Result(property = "sEmail", column = "s_email"),
        @Result(property = "sFkStatusId", column = "s_fk_status_id")
    })	Subcontractor findSubcontractorById(int sId);

}
