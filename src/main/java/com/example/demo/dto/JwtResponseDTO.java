package com.example.demo.dto;

import lombok.Builder;

// j'ai laissé builder pour la personne qui va s'occuper du ticket d'ajout de builer
@Builder
public class JwtResponseDTO {
	private String accessToken;
	private String token;

	public JwtResponseDTO() {
	}

	public JwtResponseDTO(String accessToken, String token) {
		this.accessToken = accessToken;
		this.token = token;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
