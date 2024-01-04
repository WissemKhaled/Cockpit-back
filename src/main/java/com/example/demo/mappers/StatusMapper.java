package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Status;

@Mapper
public interface StatusMapper {
	@Select("SELECT * FROM gst_status WHERE st_id = #{stId}")
	@Result(property = "stId", column = "st_id")
	@Result(property = "stName", column = "st_name")
	@Result(property = "stDescription", column = "st_Description")
	Status findStatusById(int stId);
	
	// debut cette method recupere tous les status
	@Select("SELECT * FROM gst_status ORDER BY st_id")
	@Result(property = "stId", column = "st_id")
	@Result(property = "stName", column = "st_name")
	@Result(property = "stDescription", column = "st_description")
	List<Status> getAllStatus();
}
