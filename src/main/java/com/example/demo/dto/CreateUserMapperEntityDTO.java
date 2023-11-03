package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.UUser;

@Component
public class CreateUserMapperEntityDTO {
	public CreateUserDTO toDto(UUser userInfo) {
		return new CreateUserDTO(userInfo.getUId(), userInfo.getUEmail(), userInfo.getUPassword(), userInfo.getUFirstName(), userInfo.getULastName(),
				userInfo.isUStatus(), userInfo.getUInsertionDate(), userInfo.getULastUpdate());
	}

	public UUser toUser(CreateUserDTO CreateUserDTO) {
		return new UUser(CreateUserDTO.getUId(), CreateUserDTO.getUEmail(), CreateUserDTO.getUPassword(), CreateUserDTO.getUFirstName(), CreateUserDTO.getULastName(), CreateUserDTO.isUStatus(),
				CreateUserDTO.getInsertionDate(), CreateUserDTO.getLastUpdate());
	}
}
