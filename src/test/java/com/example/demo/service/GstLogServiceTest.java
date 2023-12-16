package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.dto.CreateGstLogDTO;
import com.example.demo.dto.GstLogDTO;
import com.example.demo.dto.GstLogResponseDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.UUserMapperEntityDTO;
import com.example.demo.entity.GstLog;
import com.example.demo.entity.UUser;
import com.example.demo.exception.PasswordAvailabilityException;
import com.example.demo.mappers.CreateGstLogDtoMapper;
import com.example.demo.mappers.GstLogDtoMapper;
import com.example.demo.mappers.GstLogMapper;
import com.example.demo.mappers.UUserMapper;
import com.example.demo.utility.JsonFileLoader;

import jakarta.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"reset.password.claim.expiration.duration=15"})
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
    
    @Value("${reset.password.claim.expiration.duration}")
    private int ResetPasswordClaimExpirationDuration;

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
    
    @Test
    void testGetGstLogByValue_Success() throws NotFoundException {
        // Données de test
        String logValue = "IJIJ125JK12";
        GstLogDTO gstLogDTO = new GstLogDTO(1, "RESET_PASSWORD", "test@test.fr", logValue, LocalDateTime.now());
        GstLog gstLog = new GstLog(gstLogDTO.getLogId(), gstLogDTO.getLogType(), gstLogDTO.getLogEmail(),
                gstLogDTO.getLogValue(), gstLogDTO.getLogCreationDate());

        // Mocks
        when(gstLogMapper.getLogByValue(logValue)).thenReturn(gstLog);
        when(gstLogDtoMapper.toDto(gstLog)).thenReturn(gstLogDTO);

        // Exécution de la méthode
        GstLogDTO result = gstLogService.getGstLogByValue(logValue);

        // Vérifications
        verify(gstLogMapper, times(1)).getLogByValue(logValue);
        verify(gstLogDtoMapper, times(1)).toDto(gstLog);

        // Assertions
        assertNotNull(result, "Result should not be null");
        assertEquals(gstLogDTO, result, "DTOs should match");
    }

    @Test
    void testGetGstLogByValue_LogNotFound() {
        // Données de test
        String logValue = "NonExistentValue";

        // Mocks
        when(gstLogMapper.getLogByValue(logValue)).thenReturn(null);

        // Exécution de la méthode et vérifications
        assertThrows(NotFoundException.class, () -> gstLogService.getGstLogByValue(logValue),
                "Aucun gst log trouvé pour le type: " + logValue);

        // Vérification que les méthodes correspondantes ont été appelées
        verify(gstLogMapper, times(1)).getLogByValue(logValue);
        verify(gstLogDtoMapper, never()).toDto(any());
    }

    @Test
    void testGetGstLogByValue_InvalidArgument() {
        // Exécution de la méthode avec logValue null
        assertThrows(IllegalArgumentException.class, () -> gstLogService.getGstLogByValue(null),
                "logValue ne peut être vide ou null");

        // Exécution de la méthode avec logValue vide
        assertThrows(IllegalArgumentException.class, () -> gstLogService.getGstLogByValue(""),
                "logValue ne peut être vide ou null");

        // Vérification que les méthodes correspondantes n'ont pas été appelées
        verify(gstLogMapper, never()).getLogByValue(any());
        verify(gstLogDtoMapper, never()).toDto(any());
    }
    
    @Test
    void testCheckResetPasswordExpiration_ValidNotExpired() throws NotFoundException {
        String logValue = "IJIJ125JK12";
        GstLog gstLog = new GstLog(1, "RESET_PASSWORD", "test@test.fr", logValue, LocalDateTime.now().minusMinutes(ResetPasswordClaimExpirationDuration - 5));

        when(gstLogMapper.getLogByValue(logValue)).thenReturn(gstLog);

        boolean result = gstLogService.checkResetPasswordExpiration(logValue);

        assertTrue(result, "La demande de renouvellement de mot de passe doit être valide et non expirée");
    }

    @Test
    void testCheckResetPasswordExpiration_ValidExpired() throws NotFoundException {
        String logValue = "IJIJ125JK12";
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(ResetPasswordClaimExpirationDuration + 1);
        GstLog gstLog = new GstLog(1, "RESET_PASSWORD", "test@test.fr", logValue, expirationTime);

        when(gstLogMapper.getLogByValue(logValue)).thenReturn(gstLog);

        boolean result = gstLogService.checkResetPasswordExpiration(logValue);

        assertFalse(result, "La demande de renouvellment de mot de passe doit être expirée");
    }

    @Test
    void testCheckResetPasswordExpiration_LogNotFound() {
        String logValue = "NonExistentLog";

        when(gstLogMapper.getLogByValue(logValue)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> gstLogService.checkResetPasswordExpiration(logValue),
                "Aucun gst log trouvé pour le token : " + logValue);
    }

    @Test
    void testCheckResetPasswordExpiration_EmptyLogValue() {
        String emptyLogValue = "";

        assertThrows(IllegalArgumentException.class,
                () -> gstLogService.checkResetPasswordExpiration(emptyLogValue),
                "logValue ne peut être vide ou null");
    }
    
    @Test
    void testCheckNewPasswordAvailability_EmptyNewPwd() {
        assertThrows(IllegalArgumentException.class, () -> gstLogService.checkNewPasswordAvailability("", "test@test.com"),
                "Le paramètre newPwd ne doit pas être null ou vide");
    }

    @Test
    void testCheckNewPasswordAvailability_NewPwdMatchesCurrentPwd() {
        String email = "test@test.com";
        String newPwd = "newPassword";
        String encodedPassword = "encodedPassword";

        UUser currentUser = new UUser(1, email, "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        when(userMapper.findByEmail(email)).thenReturn(Optional.of(currentUser));
        when(encoder.matches(newPwd, encodedPassword)).thenReturn(true);

        assertThrows(PasswordAvailabilityException.class, () -> gstLogService.checkNewPasswordAvailability(newPwd, email),
                "Votre nouveau mot de passe doit être différent du mot de passe actuel");
    }

    @Test
    void testCheckNewPasswordAvailability_NewPwdMatchesLatestPwd() {
        String email = "test@test.com";
        String newPwd = "newPassword";
        String encodedPassword = "encodedPassword";

        UUser currentUser = new UUser(1, email, "123-Az", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        when(userMapper.findByEmail(email)).thenReturn(Optional.of(currentUser));
        when(encoder.matches(newPwd, encodedPassword)).thenReturn(false);
        when(encoder.matches(newPwd, currentUser.getUPassword())).thenReturn(false);

        List<GstLog> latestLogs = new ArrayList<>();
        latestLogs.add(new GstLog(1, "RESET_PASSWORD", email, "123-Azz", LocalDateTime.now()));
        latestLogs.add(new GstLog(2, "RESET_PASSWORD", email, encodedPassword, LocalDateTime.now()));
        latestLogs.add(new GstLog(3, "RESET_PASSWORD", email, "789-Def", LocalDateTime.now()));
        when(gstLogMapper.getThreeLatestLogs(email)).thenReturn(latestLogs);

        assertThrows(PasswordAvailabilityException.class, () -> gstLogService.checkNewPasswordAvailability(newPwd, email),
                "Votre nouveau mot de passe doit être différent de vos 3 derniers");
    }


    @Test
    void testCheckNewPasswordAvailability_Success() throws PasswordAvailabilityException {
        String email = "test@test.com";
        String newPwd = "newPassword";

        UUser currentUser = new UUser(1, email, "123-Az", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now().plusDays(2));

        // Corrected stubbing to match the actual invocation in the service
        when(encoder.matches(newPwd, currentUser.getUPassword())).thenReturn(false);

        List<GstLog> latestLogs = new ArrayList<>();
        latestLogs.add(new GstLog(1, "RESET_PASSWORD", email, "543-Cpl", LocalDateTime.now()));
        latestLogs.add(new GstLog(2, "RESET_PASSWORD", email, "differentEncodedPassword", LocalDateTime.now()));
        latestLogs.add(new GstLog(3, "RESET_PASSWORD", email, "123-Azz", LocalDateTime.now()));
        when(gstLogMapper.getThreeLatestLogs(email)).thenReturn(latestLogs);

        boolean result = gstLogService.checkNewPasswordAvailability(newPwd, email);
        assertTrue(result, "The new password should be available");
    }
}
