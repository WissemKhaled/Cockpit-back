package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.UUser;

@Component
public class UUserMapperEntityDTO {
	public UUserDTO toDto(UUser userInfo) {
		return new UUserDTO(userInfo.getUId(), userInfo.getUEmail(), userInfo.getUFirstName(), userInfo.getULastName(),
				userInfo.isUStatus(), userInfo.getUInsertionDate(), userInfo.getULastUpdate());
	}

	public UUser toUser(UUserDTO userInfoDto) {
		return new UUser(userInfoDto.getUId(), userInfoDto.getUEmail(), userInfoDto.getUFirstName(), userInfoDto.getULastName(), userInfoDto.isUStatus(),
				userInfoDto.getInsertionDate(), userInfoDto.getLastUpdate());
	}
}
