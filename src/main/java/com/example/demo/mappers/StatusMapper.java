package com.example.demo.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Status;

@Mapper
public interface StatusMapper {
	@Select("SELECT * FROM status WHERE st_id = #{stId}")
	Status findStatusById(int stId);
}
