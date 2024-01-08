package com.example.demo.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.UUser;

@Mapper
public interface UUserMapper {
	
Optional<UUser> findByEmail(@Param("email") String state);
	
	Optional<UUser> findById(@Param("userId") int state);
	
	void insertUser(UUser userInfo);
	
	int updatePassword(@Param("user") UUser user);
}
