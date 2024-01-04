package com.example.demo.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.demo.entity.UUser;

@Mapper
public interface UUserMapper {
	@Select("SELECT * FROM gst_user WHERE u_email = #{email}")
	@Results({
        @Result(property = "uId", column = "u_id"),
        @Result(property = "uEmail", column = "u_email"),
        @Result(property = "uPassword", column = "u_password"),
        @Result(property = "uFirstName", column = "u_first_name"),
        @Result(property = "uLastName", column = "u_last_name"),
        @Result(property = "uStatus", column = "u_status"),
        @Result(property = "uInsertionDate", column = "u_insertion_date"),
        @Result(property = "uLastUpdate", column = "u_last_update")
    })
	Optional<UUser> findByEmail(@Param("email") String state);
	
	@Select("SELECT * FROM gst_user WHERE u_id = #{userId}")
	@Results({
        @Result(property = "uId", column = "u_id"),
        @Result(property = "uEmail", column = "u_email"),
        @Result(property = "uPassword", column = "u_password"),
        @Result(property = "uFirstName", column = "u_first_name"),
        @Result(property = "uLastName", column = "u_last_name"),
        @Result(property = "uStatus", column = "u_status"),
        @Result(property = "uInsertionDate", column = "u_insertion_date"),
        @Result(property = "uLastUpdate", column = "u_last_update")
    })
	Optional<UUser> findById(@Param("id") int state);
	
	@Insert("INSERT INTO gst_user(u_email, u_password, u_first_name, u_last_name, u_status, u_insertion_date, u_last_update) VALUES (#{uEmail}, #{uPassword}, #{uFirstName}, #{uLastName}, #{uStatus}, #{uInsertionDate}, #{uLastUpdate})")
	@Options(useGeneratedKeys = true, keyProperty = "uId", keyColumn = "u_id")
	void insert(UUser userInfo);
	
	@Update("UPDATE gst_user SET u_password = #{user.uPassword}, u_last_update = #{user.uLastUpdate} WHERE u_email = #{user.uEmail}")
	int updatePassword(@Param("user") UUser user);
}
