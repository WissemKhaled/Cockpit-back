package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceProviderControllerTest {

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${token.expiration.duration}")
	private int tokenExpirationDuration;

	private String baseUrl = "/service-providers/";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetServiceProvider_ShouldReturnHttpStatusOk() throws Exception {
		int expectedSpId = 1;
		String jwtToken = createToken("john@test.fr");

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + expectedSpId).header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.spFirstName").value("sp-1-first_name"))
		.andExpect(jsonPath("$.spName").value("sp-1-name"))
		.andExpect(jsonPath("$.spEmail").value("sp-1-email"))
		.andExpect(jsonPath("$.subcontractor.sId").value(1))
		.andExpect(jsonPath("$.spStatus.stId").value(3));
	}

	private String createToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		// Add any additional claims if needed
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * tokenExpirationDuration))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
