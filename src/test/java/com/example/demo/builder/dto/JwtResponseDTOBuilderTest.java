package com.example.demo.builder.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.JwtResponseDTO;

public class JwtResponseDTOBuilderTest {

	@Test
	void testBuildJwtResponse() {
		// GIVEN
		String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQHRlc3QuZnIiLCJpYXQiOjE3MDUzMTM3NDgsImV4cCI6MTcwNTcyMzgxNH0.LZb2c-3xzNimDe9w3HYLTlXDUHarnVuqCdj9PzCSKjI";
		String token = "9e6d71c4-675f-4473-b35b-d59bc36b3b4e";

		// WHEN
		JwtResponseDTO jwtResponseDto = new JwtResponseDTOBuilder()
				.withAccessToken(accessToken)
				.withToken(token)
				.build();

		// THEN
		assertThat(jwtResponseDto).isNotNull();
		assertThat(accessToken).isEqualTo(jwtResponseDto.getAccessToken());
		assertThat(token).isEqualTo(jwtResponseDto.getToken());
	}

}
