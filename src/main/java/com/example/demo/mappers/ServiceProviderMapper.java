package com.example.demo.mappers;

import java.util.List;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Many;


import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Subcontractor;

@Mapper
public interface ServiceProviderMapper {

//	@Update("Update service_provider SET sp_fk_status_id = 4 WHERE sp_id = #{spId}")
//	@Result(property = "spId", column = "sp_id")
//	@Result(property = "status.stId", column = "sp_fk_status_id")
//	int archive(ServiceProvider serviceProvider);
//
//	@Insert("INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date, sp_fk_subcontractor_id, sp_fk_status_id)" 
//	+ "VALUES (#{spFirstName}, #{spName}, #{spEmail}, #{spCreationDate}, #{spLastUpdateDate}, #{subcontractor.sId}, #{status.stId})")
//	int insert(ServiceProvider serviceProvider);
//	
//	@Select("SELECT (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date, sp_fk_subcontractor_id, sp_fk_status_id)"
//			+ "FROM service_provider WHERE ")
//	int findServiceProvider(int spId);
	
	 @Select("SELECT * FROM subcontractor WHERE s_id = #{sId}")
        @Result(property = "sId", column = "s_id")
		@Result(property = "sName", column = "s_name")
		@Result(property = "sEmail", column = "s_email")
		@Result(property = "sCreationDate", column = "s_creation_date")
		@Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
		@Result(property = "status.stId", column = "status_stId")
		@Result(property = "status.stName", column = "status_stName")
		@Result(property = "status.stDescription", column = "status_stDescription")
	 	@Result(property = "serviceProviders", column = "s_id", javaType = List.class, many = @Many(select = "findServiceProvidersBySubcontractorId"))
	    Subcontractor getSubcontractorById(int id);

	    @Select("SELECT * FROM service_provider WHERE sp_fk_subcontractor_id = #{spId}")
	    List<ServiceProvider> findServiceProvidersBySubcontractorId(int spId);
	    
	    
	    @Select("SELECT sp_id, sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date, sp_fk_subcontractor_id, sp_fk_status_id FROM service_provider WHERE sp_fk_subcontractor_id = 1")
		@Result(property = "sId", column = "s_id")
		@Result(property = "sName", column = "s_name")
		@Result(property = "sEmail", column = "s_email")
		@Result(property = "sCreationDate", column = "s_creation_date")
		@Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
		@Result(property = "status.stId", column = "status_stId")
	    @Result(property = "subcontractor.sId", column = "sp_fk_subcontractor_id")
	    List<ServiceProvider> findServiceProvidersBySubcontractorId2(int sId);
}
