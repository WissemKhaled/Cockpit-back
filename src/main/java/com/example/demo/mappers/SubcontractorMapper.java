package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {

	//  ce code permet de renvoyer le nombre total de colonne de la
	// table subcontractor
	@Select("SELECT COUNT(*) FROM subcontractor")
	Integer countTotalItems();
	// fin
	@Select("SELECT COUNT(*) FROM subcontractor"+
	        " WHERE s_fk_status_id = ${idStatus} ")
	Integer countTotalItemsWithStatus(@Param("idStatus") Integer idStatus);
	
	@Select("SELECT * FROM status")
	@Result(property = "stId", column = "st_id")
	@Result(property = "stName", column = "st_name")
	@Result(property = "stDescription", column = "st_description")
	List<Status> getAllStatus();

	// ce code permet de renvoyer une liste de sous-traitans avec la
	// pagination est le tri grave à la requette SQL
	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as status_stId, st.st_name as status_stName, st.st_description as status_stDescription "
			+ "FROM subcontractor s " + "INNER JOIN status st ON s.s_fk_status_id = st.st_id "
			+ " ORDER BY ${nameColonne}, s_name ${sorting} LIMIT  #{offset}  OFFSET #{pageSize} ")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	List<Subcontractor> getAllSubcontractors(@Param("nameColonne") String nameColonne, @Param("sorting") String sorting,
			@Param("pageSize") int offset, @Param("offset") int pageSize);
	// fin

	
	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as status_stId, st.st_name as status_stName, st.st_description as status_stDescription "
			+ "FROM subcontractor s " + "INNER JOIN status st ON s.s_fk_status_id = st.st_id "
			+ "WHERE st.st_id= ${statusId} "
			+ "ORDER BY ${nameColonne} ${sorting} LIMIT  #{offset}  OFFSET #{pageSize} ")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	List<Subcontractor> getAllSubcontractorsWhitStatus(@Param("nameColonne") String nameColonne,
			@Param("sorting") String sorting, @Param("pageSize") int offset, @Param("offset") int pageSize,
			@Param("statusId") int statusId);

	@Insert("INSERT INTO subcontractor (s_name, s_email, s_fk_status_id) "
			+ "VALUES (#{sName}, #{sEmail}, #{status.stId})")
	@Options(useGeneratedKeys = true, keyProperty = "sId", keyColumn = "s_id")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "s_fk_status_id")
	int insertSubcontractor(Subcontractor subcontractor);

	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id as status_stId, st.st_name as status_stName, st.st_description as status_stDescription "
			+ "FROM subcontractor s " + "INNER JOIN status st ON s.s_fk_status_id = st.st_id "
			+ "WHERE s.s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	Subcontractor findSubcontractorWithStatusById(int sId);

	@Select("SELECT s.s_id, s.s_name, s.s_email, st.st_id AS status_stId, st.st_name AS status_stName, st.st_description AS status_stDescription "
			+ "FROM subcontractor s " + "JOIN status st ON s.s_fk_status_id = st.st_id " + "WHERE s.s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "status_stId")
	@Result(property = "status.stName", column = "status_stName")
	@Result(property = "status.stDescription", column = "status_stDescription")
	Subcontractor findSubcontractorById(int sId);

	@Update("UPDATE subcontractor " + "SET s_name = #{sName}, s_email = #{sEmail}, s_fk_status_id = #{status.stId} "
			+ "WHERE s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "sName", column = "s_name")
	@Result(property = "sEmail", column = "s_email")
	@Result(property = "status.stId", column = "s_fk_status_id")
	int updateSubcontractor(Subcontractor subcontractor);

	@Update("Update subcontractor SET s_fk_status_id = 4 WHERE s_id = #{sId}")
	@Result(property = "sId", column = "s_id")
	@Result(property = "status.stId", column = "s_fk_status_id")
	int archive(Subcontractor subcontractortoArchive);

}
