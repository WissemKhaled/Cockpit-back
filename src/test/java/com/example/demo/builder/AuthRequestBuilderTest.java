package com.example.demo.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.AuthRequest;

public class AuthRequestBuilderTest {
	@Test
	public void testBuildAuthRequestWithValidData() {
		// GIVEN
		String email = "john@test.com";
		String password = "123-Az";

		// WHEN
		AuthRequest authRequest = new AuthRequestBuilder()
				.withEmail(email)
				.withPassword(password)
				.build();

		// THEN
		assertThat(authRequest).isNotNull();
		assertThat(email).isEqualTo(authRequest.getEmail());
		assertThat(password).isEqualTo(authRequest.getPassword());
	}
}
