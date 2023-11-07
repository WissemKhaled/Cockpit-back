package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.entity.AuthRequest;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService service;

    @MockBean
    private JwtServiceImplementation jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private RefreshTokenService refreshTokenService;

//    @Value("${jwt.secret}")
//    private String SECRET;
//    
//    @Value("${token.expiration.duration}")
//	private int tokenExpirationDuration;
//
//    // Method to generate a test JWT token
//    private String generateTestToken() {
//        String userName = "user@example.com"; // Set the expected username
//        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * tokenExpirationDuration); // Set the expected expiration date
//
//        return Jwts.builder()
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(expirationDate)
//                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    @Test
//    public void testFindUserByEmail_WithValidToken_ShouldReturnUserDetails() throws Exception {
//        UUserDTO userDTO = new UUserDTO(1, "user@example.com", "John", "Doe", true, LocalDateTime.now(), null);
//        when(service.findUserByEmail("user@example.com")).thenReturn(userDTO);
//
//        String validToken = generateTestToken();
//        
//        System.out.println(validToken);
//
//        mockMvc.perform(get("/auth/user/userdetails")
//                .header("Authorization", "Bearer " + validToken))
//                .andExpect(status().isOk());
//    }
    
//
    @Test
    public void testAddNewUser_WithValidData_ShouldReturnCreated() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO(0, "newuser@example.com", "Password1!", "John", "Doe", true, null, null);

        mockMvc.perform(post("/auth/addNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }
//
//
//    @Test
//    public void testAuthenticateAndGetToken_WithValidCredentials_ShouldReturnStatusOk() throws Exception {
//        AuthRequest authRequest = new AuthRequest("user@example.com", "Password1!");
//
//        // Mock the behavior of the service to return an authenticated user
//        UUserDTO userDTO = new UUserDTO(1, "user@example.com", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now());
//        when(service.findUserByEmail("user@example.com")).thenReturn(userDTO);
//
//        mockMvc.perform(post("/auth/generateToken")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(authRequest)))
//                .andExpect(status().isOk());
//    }
//
//
//    @Test
//    public void testRefreshToken_WithValidToken_ShouldReturnToken() throws Exception {
//        RefreshTokenRequestDTO refreshTokenRequestDTO = new RefreshTokenRequestDTO("validRefreshToken");
//
//        // Mock the behavior of the refreshTokenService
//        when(refreshTokenService.findByToken("validRefreshToken")).thenReturn(Optional.of(new RefreshToken()));
//
//        // Mock the JWT service to generate a new token
//        when(jwtService.generateToken(any())).thenReturn("newAccessToken");
//
//        mockMvc.perform(post("/auth/refreshToken")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(refreshTokenRequestDTO)))
//                .andExpect(status().isOk());
//    }

}
