package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.ServiceProvider;

@Mapper
public interface ServiceProviderMapper {
	
	@Update("Update gst_service_provider SET sp_fk_status_id = 4 WHERE sp_id = #{spId}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spStatus.stId", column = "sp_fk_status_id")
	int archiveServiceProvider(ServiceProvider serviceProvider);
	
	
	@Insert("INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date, sp_fk_subcontractor_id, sp_fk_status_id)"
			+ "VALUES (#{spFirstName}, #{spName}, #{spEmail}, #{spCreationDate}, #{spLastUpdateDate}, #{subcontractor.sId}, #{spStatus.stId})")
	@Options(useGeneratedKeys = true, keyProperty = "spId", keyColumn = "sp_id")
	int insertServiceProvider(ServiceProvider serviceProvider);
	
	
	@Update("UPDATE gst_service_provider SET sp_first_name = #{spFirstName}, sp_name = #{spName}, sp_email = #{spEmail}, sp_lastUpdate_date = #{spLastUpdateDate}, "
			+ "sp_fk_status_id = #{spStatus.stId}, sp_fk_subcontractor_id = #{subcontractor.sId} "
			+ "WHERE sp_id = #{spId}")
	@Options(useGeneratedKeys = true, keyProperty = "spId", keyColumn = "sp_id")
	int updateServiceProvider(ServiceProvider serviceProvider);
	
	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, sp.sp_fk_subcontractor_id, sp.sp_fk_status_id "
			+ "FROM gst_service_provider sp "
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
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN gst_status st ON sp.sp_fk_status_id = st.st_id "
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
	
	
	@Select("SELECT sp.sp_id, sp.sp_email "
			+ "FROM gst_service_provider sp "
			+ "WHERE sp.sp_email = #{spEmail}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spEmail", column = "sp_email")
	ServiceProvider findServiceProviderBySpEmail(String spEmail);

	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "WHERE sp_fk_subcontractor_id = #{sId} "
			+ "ORDER BY sp_id")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "subcontractor.sId", column = "subcontractor_sId")
	@Result(property = "subcontractor.sName", column = "subcontractor_sName")
	List<ServiceProvider> findServiceProvidersBySubcontractorId(int sId);
	
	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, "
			+ "st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN gst_status st ON sp.sp_fk_status_id = st.st_id "
			+ "WHERE st.st_id != 4 "
			+ "ORDER BY subcontractor_sName, st.st_id, sp.sp_name ${sorting} LIMIT #{offset} OFFSET #{pageSize}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "spStatus.stId", column = "status_stId")
	@Result(property = "spStatus.stName", column = "status_stName")
	@Result(property = "subcontractor.sName", column = "subcontractor_sName")
	List<ServiceProvider> findAllNonArchivedServiceProviders(
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize);
	

	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, "
			+ "st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN gst_status st ON sp.sp_fk_status_id = st.st_id "
			+ "WHERE st.st_id = ${statusId} "
			+ "ORDER BY subcontractor_sName, sp.sp_name ${sorting} LIMIT #{offset} OFFSET #{pageSize}")
	@Result(property = "spId", column = "sp_id")
	@Result(property = "spFirstName", column = "sp_first_name")
	@Result(property = "spName", column = "sp_name")
	@Result(property = "spEmail", column = "sp_email")
	@Result(property = "spCreationDate", column = "sp_creation_date")
	@Result(property = "spLastUpdateDate", column = "sp_lastUpdate_date")
	@Result(property = "spStatus.stId", column = "status_stId")
	@Result(property = "spStatus.stName", column = "status_stName")
	@Result(property = "subcontractor.sName", column = "subcontractor_sName")
	List<ServiceProvider> findAllServiceProvidersFlitredByStatus(
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize, 
			@Param("statusId") int statusId);
	
	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN gst_status st ON sp.sp_fk_status_id = st.st_id "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND st.st_id = ${statusId} "
			+ "ORDER BY  subcontractor_sName, st.st_id, sp.sp_email LIMIT #{offset} OFFSET #{pageSize}")
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
	List<ServiceProvider> findAllServiceProvidersByCriteriaAndFiltredByStatus(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize, 
			@Param("statusId") int statusId);
	
	
	@Select("SELECT sp.sp_id, sp.sp_first_name, sp.sp_name, sp.sp_email, sp.sp_creation_date, sp.sp_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName, "
			+ "s.s_id as subcontractor_sId, s.s_name as subcontractor_sName " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "INNER JOIN gst_status st ON sp.sp_fk_status_id = st.st_id "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND st.st_id != 4 "
			+ "ORDER BY  subcontractor_sName, st.st_id, sp.sp_email LIMIT #{offset} OFFSET #{pageSize}")
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
	List<ServiceProvider> findServiceProvidersByCriteria(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize") int offset,
			@Param("offset") int pageSize);
	
	
	@Select("SELECT COUNT(*) FROM gst_service_provider WHERE sp_fk_status_id != 4")
	int countAllNonArchivedServiceProviders();

	
	@Select("SELECT COUNT(*) FROM gst_service_provider WHERE sp_fk_status_id = ${statusId}")
	int countAllServiceProvidersFiltredByStatus(int statusId);

	
	@Select("SELECT COUNT(*) " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND sp.sp_fk_status_id != 4 ")
	int findNumberOfAllServiceProvidersByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms);
	
	
	@Select("SELECT COUNT(*) " 
			+ "FROM gst_service_provider sp "
			+ "INNER JOIN gst_subcontractor s ON sp.sp_fk_subcontractor_id = s.s_id "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND sp.sp_fk_status_id = ${statusId} ")
	int findNumberOfAllServiceProvidersByByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("statusId") int statusId);
	
}
