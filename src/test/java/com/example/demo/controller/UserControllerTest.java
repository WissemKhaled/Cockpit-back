package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.implementation.JwtServiceImplementation;
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

    /**
	 * test la création d'un User
     * Test le cas de figure où un utilisateur est correctement inséré en bdd
     * 
    */
    @Test
    public void testAddNewUser_WithValidData_ShouldReturnCreated() throws Exception {
        CreateUserDTO userDTO = new CreateUserDTO(0, "newuser@example.com", "Password1!", "John", "Doe", true, null, null);

        mockMvc.perform(post("/auth/addNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }
    
    /**
	 * test la création d'un User
     * Test le cas de figure où l'insertion du User en bdd échoue à cause d'un email null
     * 
    */
    @Test
    public void testAddNewUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Create a user with missing email
        CreateUserDTO userDTO = new CreateUserDTO(0, null, "Password1!", "John", "Doe", true, null, null);

        mockMvc.perform(post("/auth/addNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

}
