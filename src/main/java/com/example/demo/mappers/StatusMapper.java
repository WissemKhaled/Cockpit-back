package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Status;

@Mapper
public interface StatusMapper {

	Status findStatusById(@Param("stId") int stId);
	
	List<Status> getAllStatus();
}
