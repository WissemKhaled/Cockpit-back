package com.example.demo.entity.builder;

import com.example.demo.dto.JwtResponseDTO;

public class JwtResponseDTOBuilder {
	private String accessToken;
	private String token;

	public JwtResponseDTOBuilder withAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public JwtResponseDTOBuilder withToken(String token) {
		this.token = token;
		return this;
	}

	public JwtResponseDTO build() {
		return new JwtResponseDTO(accessToken, token);
	}

}
