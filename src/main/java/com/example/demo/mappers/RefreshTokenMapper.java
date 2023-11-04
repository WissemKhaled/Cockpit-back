package com.example.demo.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.RefreshToken;

public interface RefreshTokenMapper {
	/*
	 * Trouve le refresh token par son id
	 */
	@Select("SELECT rt.rt_id, rt.rt_token, rt.rt_expiry_date, u.u_id as uId, u.u_email, u.u_password, u.u_first_name, u.u_last_name, u.u_status, u.u_insertion_date, u.u_last_update "
			+ "FROM refresh_token rt " 
			+ "INNER JOIN u_user u ON rt.rt_fk_user_id = u.u_id "
			+ "WHERE rt.rt_token = #{token}")
	@Results({
		@Result(property = "rtId", column = "rt_id"), 
		@Result(property = "rtToken", column = "rt_token"),
		@Result(property = "rtExpiryDate", column = "rt_expiry_date"), 
		@Result(property = "u.uId", column = "u_id"),
		@Result(property = "uUser.uEmail", column = "u_email"),
		@Result(property = "uUser.uPassword", column = "u_password"),
		@Result(property = "uUser.uFirstName", column = "u_first_name"),
		@Result(property = "uUser.uLastName", column = "u_last_name"),
		@Result(property = "uUser.uStatus", column = "u_status"),
		@Result(property = "uUser.uInsertionDate", column = "u_insertion_date"),
		@Result(property = "uUser.uLastUpdate", column = "u_last_update") 
	})
	Optional<RefreshToken> findByToken(@Param("token") String token);

	@Select("SELECT rt.rt_id, rt.rt_token, rt.rt_expiry_date, u.u_id as uId, u.u_email, u.u_password, u.u_first_name, u.u_last_name, u.u_status, u.u_insertion_date, u.u_last_update "
			+ "FROM refresh_token rt " 
			+ "INNER JOIN u_user u ON rt.rt_fk_user_id = u.u_id "
			+ "WHERE rt.rt_id = #{rtId}")
	@Results({ 
		@Result(property = "rtId", column = "rt_id"), 
		@Result(property = "rtToken", column = "rt_token"),
		@Result(property = "rtExpiryDate", column = "rt_expiry_date"), 
		@Result(property = "u.uId", column = "u_id"),
		@Result(property = "uUser.uEmail", column = "u_email"),
		@Result(property = "uUser.uPassword", column = "u_password"),
		@Result(property = "uUser.uFirstName", column = "u_first_name"),
		@Result(property = "uUser.uLastName", column = "u_last_name"),
		@Result(property = "uUser.uStatus", column = "u_status"),
		@Result(property = "uUser.uInsertionDate", column = "u_insertion_date"),
		@Result(property = "uUser.uLastUpdate", column = "u_last_update")
	})
	Optional<RefreshToken> findRefreshTokenWithUserByUserId(@Param("rtId") int rtId);

	
//	@Insert("INSERT INTO refresh_token(rt_token, rt_expiry_date, rt_fk_user_id) " +
//	        "VALUES (#{rtToken}, #{rtExpiryDate}, #{uUser.uId})")
//	@Options(useGeneratedKeys = true, keyProperty = "rtId", keyColumn = "rt_id")
//	int insert(RefreshToken refreshToken);
	
	@Insert("INSERT INTO refresh_token(rt_token, rt_expiry_date, rt_fk_user_id) " +
	        "VALUES (#{rtToken}, TO_TIMESTAMP(#{rtExpiryDate}, 'YYYY-MM-DD HH24:MI:SS'), #{uUser.uId})")
	@Options(useGeneratedKeys = true, keyProperty = "rtId", keyColumn = "rt_id")
	int insert(RefreshToken refreshToken);
	
	/*
	 * Supprime le refresh token par le userId
	 */
	@Delete("DELETE FROM refresh_token WHERE rt_fk_user_id = #{userId}")
	void deleteByUserId(@Param("userId") int userId);

	/*
	 * Supprime le refresh token par le token (token key)
	 */
	@Delete("DELETE FROM refresh_token WHERE rt_token = #{token}")
	void delete(@Param("token") String token);

}