package com.example.demo.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Status;

@Mapper
public interface StatusMapper {
	@Select("SELECT * FROM status WHERE st_id = #{stId}")
	@Results({ @Result(property = "stId", column = "st_id"), @Result(property = "stName", column = "st_name"),
			@Result(property = "stDescription", column = "st_Description") })
	Status findStatusById(int stId);
}
