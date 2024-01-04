package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {
	// ce code permet de renvoyer le nombre total de colonne de la
	// table subcontractor
	@Select("SELECT COUNT(*) "
			+ "FROM gst_subcontractor "
			+ "WHERE s_fk_status_id != 4")
	Integer countAllNonArchivedSubcontractors();


	@Select("SELECT COUNT(*) "
			+ "FROM gst_subcontractor "
			+ "WHERE s_fk_status_id = ${idStatus} ")
	Integer countAllNonArchivedSubcontractorsWithStatus(@Param("idStatus") Integer idStatus);


	// ce code permet de renvoyer une liste de sous-traitans avec la
	// pagination est le tri grave à la requette SQL
	@Select("SELECT s.s_id, s.s_name, s.s_email, s.s_creation_date, s.s_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName "
			+ "FROM gst_subcontractor s "
			+ "INNER JOIN gst_status st ON s.s_fk_status_id = st.st_id "
			+ "WHERE s.s_fk_status_id != 4 "
			+ "ORDER BY s.s_fk_status_id, s_name ${sorting} LIMIT #{offset} OFFSET #{pageSize} ")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "sCreationDate", column = "s_creation_date")
	@Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	List<Subcontractor> findAllNonArchivedSubcontractors(
			@Param("nameColonne") String nameColonne, 
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize);

	
	// ce code permet de renvoyer une liste de sous-traitans avec la
	// pagination est le tri grave à la requette SQL
	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as status_stId, st.st_name as status_stName "
			+ "FROM gst_subcontractor s "
			+ "INNER JOIN gst_status st ON s.s_fk_status_id = st.st_id "
			+ "WHERE st.st_id= ${statusId} "
			+ "ORDER BY ${nameColonne}  ${sorting} LIMIT  #{offset}  OFFSET #{pageSize} ")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	List<Subcontractor> findAllSubcontractorsWithStatus(
			@Param("nameColonne") String nameColonne,
			@Param("sorting") String sorting,
			@Param("pageSize") int offset,
			@Param("offset") int pageSize,
			@Param("statusId") int statusId);

	
	@Select("SELECT s.s_id, s.s_name, s.s_email, s.s_creation_date, s.s_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName "
			+ "FROM gst_subcontractor s "
			+ "INNER JOIN gst_status st ON s.s_fk_status_id = st.st_id "
			+ "WHERE s.s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "sCreationDate", column = "s_creation_date")
	@Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	Subcontractor findSubcontractorWithStatusById(int sId);

	
	@Select("SELECT s.s_id, s.s_name "
			+ "FROM gst_subcontractor s "
			+ "WHERE s.s_name = #{sName}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	Subcontractor findSubcontractorWithStatusBySName(String sName);

	
	@Select("SELECT s.s_id, s.s_email "
			+ "FROM gst_subcontractor s "
			+ "WHERE s.s_email = #{sEmail}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sEmail", column = "s_email")
	Subcontractor findSubcontractorWithStatusBySEmail(String sEmail);

	
	@Insert("INSERT INTO gst_subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id) "
			+ "VALUES (#{sName}, #{sEmail}, #{sCreationDate},#{sLastUpdateDate}, #{status.stId})")
	@Options(useGeneratedKeys = true, keyProperty = "sId", keyColumn = "s_id")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "sCreationDate", column = "s_CreationDate")
	@Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
	@Result(property = "status.stId", column = "s_fk_status_id")
	int insertSubcontractor(Subcontractor subcontractor);

	
	@Update("UPDATE gst_subcontractor "
			+ "SET s_name = #{sName}, s_email = #{sEmail}, s_lastUpdate_date = #{sLastUpdateDate}, s_fk_status_id = #{status.stId} "
			+ "WHERE s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "sLastUpdate", column = "s_last_update")
	@Result(property = "status.stId", column = "s_fk_status_id")
	int updateSubcontractor(Subcontractor subcontractor);

	
	@Update("Update gst_subcontractor SET s_fk_status_id = 4 WHERE s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "status.stId", column = "s_fk_status_id")
	int archiveSubcontractor(Subcontractor subcontractortoArchive);


	@Select("SELECT s.s_id, s.s_name, s.s_email, s.s_creation_date, s.s_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName "
			+ "FROM gst_subcontractor s "
			+ "INNER JOIN gst_status st ON s.s_fk_status_id = st.st_id "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND s.s_fk_status_id != 4")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "sCreationDate", column = "s_creation_date")
	@Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	List<Subcontractor> findAllSubcontractorsByCriteria(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize")int offset,
			@Param("offset") int pageSize);
	
	
	@Select("SELECT s.s_id, s.s_name, s.s_email, s.s_creation_date, s.s_lastUpdate_date, st.st_id as status_stId, st.st_name as status_stName "
	        + "FROM gst_subcontractor s "
	        + "INNER JOIN gst_status st ON s.s_fk_status_id = st.st_id "
	        + "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
	        + "AND st.st_id = #{statusId}")
	        @Result(property = "sId", column = "s_id")
	        @Result(property = "sName", column = "s_name")
	        @Result(property = "sEmail", column = "s_email")
	        @Result(property = "sCreationDate", column = "s_creation_date")
	        @Result(property = "sLastUpdateDate", column = "s_lastUpdate_date")
	        @Result(property = "status.stId", column = "status_stId")
	        @Result(property = "status.stName", column = "status_stName")
	List<Subcontractor> findAllSubcontractorsByCriteriaAndFiltredByStatus(
	        @Param("columnName") String columnName,
	        @Param("searchTerms") String searchTerms,
	        @Param("pageSize") int offset,
	        @Param("offset") int pageSize,
	        @Param("statusId") int statusId);


	@Select("SELECT COUNT(*) " 
			+ "FROM gst_subcontractor s "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND s.s_fk_status_id != 4 ")
	Integer findNumberOfAllSubcontractorsByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms);
	
	
	@Select("SELECT COUNT(*) " 
			+ "FROM gst_subcontractor s "
			+ "WHERE ${columnName} ILIKE #{searchTerms} || '%' "
			+ "AND s.s_fk_status_id = ${statusId} ")
	Integer findNumberOfAllSubcontractorsByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("statusId") int statusId);

}
