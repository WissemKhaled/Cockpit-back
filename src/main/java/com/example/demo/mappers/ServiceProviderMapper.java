package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Many;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Subcontractor;

@Mapper
public interface ServiceProviderMapper {
	
	@Update("Update service_provider SET sp_fk_status_id = 4 WHERE sp_id = #{spId}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spStatus.stId", column = "sp_fk_status_id")
	int archiveServiceProvider(ServiceProvider serviceProvider);
	
	@Insert("INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date, sp_fk_subcontractor_id, sp_fk_status_id)"
			+ "VALUES (#{spFirstName}, #{spName}, #{spEmail}, #{spCreationDate}, #{spLastUpdateDate}, #{subcontractor.sId}, #{spStatus.stId})")
	@Options(useGeneratedKeys = true, keyProperty = "spId", keyColumn = "sp_id")
	int insertServiceProvider(ServiceProvider serviceProvider);
	
	@Update("UPDATE service_provider SET sp_first_name = #{spFirstName}, sp_name = #{spName}, sp_email = #{spEmail}, sp_lastUpdate_date = #{spLastUpdateDate}, "
			+ "sp_fk_status_id = #{spStatus.stId}, sp_fk_subcontractor_id = #{subcontractor.sId} "
			+ "WHERE sp_id = #{spId}")
	@Options(useGeneratedKeys = true, keyProperty = "spId", keyColumn = "sp_id")
	int updateServiceProvider(ServiceProvider serviceProvider);
	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, sp.sp_fk_subcontractor_id, sp.sp_fk_status_id "
			+ "FROM service_provider sp "
			+ "WHERE sp.sp_id = #{spId}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "subcontractor.sId", column = "sp_fk_subcontractor_id")
	@Result(property = "spStatus.stId", column = "sp_fk_status_id")
	ServiceProvider findServiceProviderById(int spId);
	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName, s.s_email as subcontractor_sEmail " 
			+ "FROM service_provider sp "
			+ "INNER JOIN subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN status st ON sp.sp_fk_status_id = st.st_id "
			+ "WHERE sp.sp_id = #{spId}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "spStatus.stId", column = "status_stId")
	@Result(property = "spStatus.stName", column = "status_stName")
	@Result(property = "subcontractor.sId", column = "subcontractor_sId")
	@Result(property = "subcontractor.sName", column = "subcontractor_sName")
	@Result(property = "subcontractor.sEmail", column = "subcontractor_sEmail")
	ServiceProvider findServiceProviderWithSubcontractorBySpId(int sId);
	

	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, sp.sp_fk_subcontractor_id, sp.sp_fk_status_id "
			+ "FROM service_provider sp "
			+ "WHERE sp.sp_email = #{spEmail}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "subcontractor.sId", column = "sp_fk_subcontractor_id")
	@Result(property = "spStatus.stId", column = "sp_fk_status_id")
	ServiceProvider findServiceProviderBySpEmail(String spEmail);

	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM service_provider sp "
			+ "INNER JOIN subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN status st ON sp.sp_fk_status_id = st.st_id "
			+ "WHERE sp_fk_subcontractor_id = #{sId} "
			+ "ORDER BY sp_id")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "spStatus.stId", column = "status_stId")
	@Result(property = "spStatus.stName", column = "status_stName")
	@Result(property = "subcontractor.sId", column = "subcontractor_sId")
	@Result(property = "subcontractor.sName", column = "subcontractor_sName")
	List<ServiceProvider> findServiceProvidersBySubcontractorId(int sId);
	
    @Select("SELECT s.s_id, s.s_name, s.s_email, s.s_creation_date, s.s_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName, st.st_description as status_stDescription "
            + "FROM subcontractor s " + "INNER JOIN status st ON s.s_fk_status_id = st.st_id "
            + "WHERE s.s_id = #{sId}")
    @Result(property = "sId", column = "s_id")
    @Result(property = "sName", column = "s_name")
    @Result(property = "sEmail", column = "s_email")
    @Result(property = "sCreationDate", column = "s_creation_date")
    @Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
    @Result(property = "status.stId", column = "status_stId")
    @Result(property = "status.stName", column = "status_stName")
    @Result(property = "status.stDescription", column = "status_stDescription")
    @Result(property = "serviceProviders", column = "s_id", javaType = List.class, many = @Many(select = "findServiceProvidersBySubcontractorId"))
    Subcontractor findSubcontractorWithServiceProvidersBySubcontractorId(int sId);


	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM service_provider sp "
			+ "INNER JOIN subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN status st ON sp.sp_fk_status_id = st.st_id "
			+ "ORDER BY subcontractor_sName,status_stId, sp_name ")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "spStatus.stId", column = "status_stId")
	@Result(property = "spStatus.stName", column = "status_stName")
	@Result(property = "subcontractor.sName", column = "subcontractor_sName")
	List<ServiceProvider> findAllServiceProviders();
	

}
