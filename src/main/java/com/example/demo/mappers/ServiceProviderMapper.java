package com.example.demo.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.ServiceProvider;

@Mapper
public interface ServiceProviderMapper {
	
	@Update("Update service_provider SET sp_fk_status_id = 4 WHERE sp_id = #{spId}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "status.stId", column = "sp_fk_status_id")
	int archive(ServiceProvider serviceProvidertoArchive);
}
