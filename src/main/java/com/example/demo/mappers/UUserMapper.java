package com.example.demo.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.UUser;

@Mapper
public interface UUserMapper {

	Optional<UUser> findUserByEmail(@Param("uEmail") String uEmail);

	Optional<UUser> findUserById(@Param("userId") int userId);

	void insertUser(UUser userInfo);

	int updatePassword(@Param("user") UUser user);
}
