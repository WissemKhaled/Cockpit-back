package com.example.demo.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.RefreshToken;

@Mapper
public interface RefreshTokenMapper {

	/*
	 * Trouve le refresh token par sa clé de token
	 */
	Optional<RefreshToken> findRefreshTokenByToken(@Param("token") String token);

	/*
	 * Trouve le refresh token par userId
	 */
	Optional<RefreshToken> findRefreshTokenByUserId(@Param("userId") int userId);

	/*
	 * Insère le refresh token en base de donnée
	 */
//	@Options(useGeneratedKeys = true, keyProperty = "rtId", keyColumn = "rt_id")
	int insertRefreshToken(RefreshToken refreshToken);

	/*
	 * Supprime le refresh token par le token (token key)
	 */
	void deleteRefreshTokenByToken(@Param("token") String token);

	/*
	 * Supprime le refresh token par le userId
	 */
	void deleteRefreshTokenById(@Param("userId") int userId);

}