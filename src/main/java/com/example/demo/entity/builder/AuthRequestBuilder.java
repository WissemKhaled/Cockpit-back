package com.example.demo.entity.builder;

import com.example.demo.entity.AuthRequest;

public class AuthRequestBuilder {
	private String email;
	private String password;

	public AuthRequestBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public AuthRequestBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public AuthRequest build() {
		return new AuthRequest(email, password);
	}
}
