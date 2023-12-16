package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogResponseDTO;
import com.example.demo.service.GstLogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class GstLogControllerTest {
    @Mock
    private GstLogServiceImpl gstLogService;

    @InjectMocks
    private GstLogController gstLogController;

    @Autowired
    private MockMvc mockMvc;
    
    private String baseUrl = "/gstlogs";
    
    /**
     * Test la création d'un log
     * Ne fonctionne qu'avec un email d'un user existant dont le statut est actif en base de donné
     * Si le test passe, un email de redéfinition de mot de passe est envoyé et le log est sauvegardé en bdd
    */
    @Test
    void testCreateGstLog_Success() throws Exception {
        CreateGstLogDTO createGstLogDTO = new CreateGstLogDTO();
        createGstLogDTO.setLogId(9991);
        createGstLogDTO.setLogType("RESET_PASSWORD");
        createGstLogDTO.setLogEmail("cockpit.open@gmail.com");
        createGstLogDTO.setLogValue("123SDER56ZS");
        createGstLogDTO.setLogCreationDate(LocalDateTime.now());
        GstLogResponseDTO successResponse = new GstLogResponseDTO("success", "Log created successfully");

        when(gstLogService.saveGstLog(createGstLogDTO)).thenReturn(successResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/createGstLog")
                .content(asJsonString(createGstLogDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
                .andExpect(status().isCreated());
    }
    
    /**
     * Test le cas de figure où l'utilisateur est inactif
     * l'envoie d'email n'es pas exécuté
    */
    @Test
    void testCreateGstLog_BadRequest_InactiveUser() throws Exception {
    	CreateGstLogDTO createGstLogDTO = new CreateGstLogDTO();
    	createGstLogDTO.setLogId(9991);
    	createGstLogDTO.setLogType("RESET_PASSWORD");
    	createGstLogDTO.setLogEmail("sam@test.fr");
    	createGstLogDTO.setLogValue("856SDER56KJ");
    	createGstLogDTO.setLogCreationDate(LocalDateTime.now());
        GstLogResponseDTO badRequestResponse = new GstLogResponseDTO("error", "L'utilisateur est inactif");

        when(gstLogService.saveGstLog(createGstLogDTO)).thenReturn(badRequestResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/createGstLog")
                .content(asJsonString(createGstLogDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("L'utilisateur est inactif"))
                .andExpect(status().isBadRequest());
    }
    
    /**
     * Test le cas de figure où l'email est inexistant
     * une erreur est retournée et aucun email n'est donc envoyé
    */
    @Test
    void testCreateGstLog_BadRequest_InexistantUser() throws Exception {
        CreateGstLogDTO createGstLogDTO = new CreateGstLogDTO();
        createGstLogDTO.setLogId(9991);
        createGstLogDTO.setLogType("RESET_PASSWORD");
        createGstLogDTO.setLogEmail("giuseppe@rossoneri.it");
        createGstLogDTO.setLogValue("993NDER56FV");
        createGstLogDTO.setLogCreationDate(LocalDateTime.now());
        
        GstLogResponseDTO badRequestResponse = new GstLogResponseDTO("error", "Aucun utilisateur trouvé avec l'email " + createGstLogDTO.getLogEmail());

        when(gstLogService.saveGstLog(createGstLogDTO)).thenReturn(badRequestResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/createGstLog")
                .content(asJsonString(createGstLogDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(badRequestResponse.getMessage()))
                .andExpect(status().isBadRequest());
    }

    // méthode pour convertir un objet vers sa representation JSON
    public static String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Ajout de module Jackson pour Java 8 date/time types
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
