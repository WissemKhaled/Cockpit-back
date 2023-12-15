package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogResponseDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.UUserMapperEntityDTO;
import com.example.demo.entity.GstLog;
import com.example.demo.entity.UUser;
import com.example.demo.mappers.CreateGstLogDtoMapper;
import com.example.demo.mappers.GstLogDtoMapper;
import com.example.demo.mappers.GstLogMapper;
import com.example.demo.mappers.UUserMapper;
import com.example.demo.utility.JsonFileLoader;

import jakarta.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
public class GstLogServiceTest {

    @Mock
    private GstLogMapper gstLogMapper;

    @Mock
    private CreateGstLogDtoMapper createGstLogDtoMapper;

    @Mock
    private GstLogDtoMapper gstLogDtoMapper;

    @Mock
    private MailSenderService mailService;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JsonFileLoader jsonFileLoader;
    
    @Mock
    private UUserMapperEntityDTO userMapperEntityDTO;

    @Mock
    private UUserMapper userMapper;

    @InjectMocks
    private GstLogServiceImpl gstLogService;

    @Test
    public void testSaveGstLog_Success() throws MessagingException {
    	// Données de test pour le nouvel utilisateur
    	int existingId = 1;
    	String email = "john@test.fr";
    	UUserDTO dummyUserDTO = new UUserDTO(existingId, email, "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
    	
    	// Mock de la conversion de DTO en entité utilisateur
        UUser dummyUser = new UUser(dummyUserDTO.getUId(), dummyUserDTO.getUEmail(),
        		dummyUserDTO.getUFirstName(), dummyUserDTO.getULastName(), dummyUserDTO.isUStatus(),
        		dummyUserDTO.getInsertionDate(), dummyUserDTO.getLastUpdate());
        
        // exécution de la méthode findUserByEmail
        when(userInfoService.findUserByEmail(email)).thenReturn(dummyUserDTO);

        // Données de test pour le nouveau log
        CreateGstLogDTO dummyGstLogDto = new CreateGstLogDTO(existingId, "RESET_PASSWORD", "john@test.fr", "IJIJ125JK12", LocalDateTime.now());
        
        
        // Mock de la conversion de DTO en entité GstLog
        GstLog dummyGstLog = new GstLog(dummyGstLogDto.getLogId(), dummyGstLogDto.getLogType(), dummyGstLogDto.getLogEmail(),
        		dummyGstLogDto.getLogValue(), dummyGstLogDto.getLogCreationDate());
        
        // exécution de la méthode insertLog
        when(createGstLogDtoMapper.toGstLog(dummyGstLogDto)).thenReturn(dummyGstLog);
        when(gstLogMapper.insertLog(dummyGstLog)).thenReturn(1);
        
        GstLogResponseDTO responseDTO = gstLogService.saveGstLog(dummyGstLogDto);

        // Vérification que les méthodes nécessaires sont bien appellés
        verify(userInfoService, times(1)).findUserByEmail(email);
        verify(gstLogMapper, times(1)).insertLog(dummyGstLog);

        // Vérification que la réponse attendu est un succès
        assertEquals("success", responseDTO.getStatus());
        assertNotNull(responseDTO.getMessage());
    }

    @Test
    public void testSaveGstLog_UserNotFound() throws MessagingException {
        // Données de test pour le nouvel utilisateur
        int existingId = 1;
        String email = "john@test.fr";
        
        // Mock de la conversion de DTO en entité utilisateur
        when(userInfoService.findUserByEmail(email)).thenReturn(null);  // Simulation d'un utilisateur non trouvé

        // Données de test pour le nouveau log
        CreateGstLogDTO dummyGstLogDto = new CreateGstLogDTO(existingId, "RESET_PASSWORD", "john@test.fr", "IJIJ125JK12", LocalDateTime.now());

        GstLogResponseDTO responseDTO = gstLogService.saveGstLog(dummyGstLogDto);

        // Vérification que la méthode findUserByEmail a bien été appelée
        verify(userInfoService, times(1)).findUserByEmail(email);

        // Vérification que la méthode insertLog n'a pas été appelée car l'utilisateur n'a pas été trouvé
        verify(gstLogMapper, times(0)).insertLog(any());

        // Vérification que la réponse attendue est un échec
        assertEquals("error", responseDTO.getStatus());
        assertNotNull(responseDTO.getMessage());
        assertEquals("Aucun utilisateur trouvé avec l'email " + email, responseDTO.getMessage());
    }
}
