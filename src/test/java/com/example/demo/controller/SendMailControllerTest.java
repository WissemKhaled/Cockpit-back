package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SendMailDTO;
import com.example.demo.service.SendMailService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // pour utiliser @BeforeAll et @AfterAll dans les méthodes non
												// statiques,on doit ajouter à notre test
												// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
												// pour éviter la lancement de JUnitException
public class SendMailControllerTest {

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${token.expiration.duration}")
	private int tokenExpirationDuration;

	private String baseUrl = "/SendMail/";
	private String jwtToken;

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
    private SendMailService mailService;

    @InjectMocks
    private SendMailController controller;

	@BeforeAll
	private void setUpBeforeClass() {
		jwtToken = createToken("john@test.fr");
	}
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Test
	public void whenFileUploaded_thenVerifyStatus() 
	  throws Exception {
	    MockMultipartFile file  = new MockMultipartFile(
	        "file", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
	    
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        
        when(mailService.saveAndSendMail(any(SendMailDTO.class), any(List.class)))
        .thenReturn("Opération réussie");


        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON))  // Remplacez par le JSON réel de SendMailDTO
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.msTo").value("Sp1@email.com")) 
            .andExpect(jsonPath("$.msCc").value("Sp1@email.com"))
            .andExpect(jsonPath("$.msSubject").value("Sujet"))
            .andExpect(jsonPath("$.msBody").value("bod"))
            .andExpect(jsonPath("$.msSendDate").value(null))
            .andExpect(jsonPath("$.msFkUserId").value(1));
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
