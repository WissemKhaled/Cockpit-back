package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {
//	@Select("SELECT * FROM subcontractor WHERE name = #{name}")
//	Optional<Subcontractor> findByName(String name);

//	@Select("SELECT * FROM subcontractor WHERE id = #{id}")
//	Optional<Subcontractor> getSubcontractorById(int userId);

//	@Select("SELECT * FROM subcontractor")
//	List<Subcontractor> getAllSubcontractors();

	@Insert("INSERT INTO subcontractor (name, email, status) VALUES (#{name}, #{email}, #{status})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void insertSubcontractor(Subcontractor subcontractor);
	
//	@Insert("INSERT INTO subcontractor (name, email, subcontractorStatus) VALUES (#{name}, #{email}, #{subcontractorStatus, typeHandler=com.example.demo.mappers.SubcontractorStatusTypeHandler})")
//	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
//	void insertSubcontractor(Subcontractor subcontractor);

	
    @Update("UPDATE subcontractor SET name = #{name}, email = #{email}, status = #{status} WHERE id = #{id}")
	void updateSubcontractor(Subcontractor subcontractor);
    
    @Select("SELECT * FROM subcontractor WHERE id = #{id}")
    Subcontractor findSubcontractorById(int id);

}
