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
    private UUserMapper userMapper;

    @InjectMocks
    private GstLogServiceImpl gstLogService;

    @Test
    public void testSaveGstLog_Success() throws MessagingException {
        // Mock dependencies
    	int existingId = 1;
    	UUserDTO dummyUser = new UUserDTO(existingId, "john@test.fr", "John", "Doe", true, null, null);

        when(userInfoService.findUserByEmail(any())).thenReturn(dummyUser);

        // Execute the method
        CreateGstLogDTO dummyGstLogDto = new CreateGstLogDTO(existingId, "RESET_PASSWORD", "john@test.fr", "IJIJ125JK12", LocalDateTime.now());
        GstLogResponseDTO responseDTO = gstLogService.saveGstLog(dummyGstLogDto);
        
        GstLog dummyGstLog = createGstLogDtoMapper.toGstLog(dummyGstLogDto);
        
        // gstLogMapper.insertLog(gstLog);
        
        when(gstLogMapper.insertLog(dummyGstLog)).thenReturn(1);

        // Verify that the necessary methods were called
        verify(userInfoService, times(1)).findUserByEmail(any());
        verify(gstLogMapper, times(1)).insertLog(dummyGstLog);
        verify(mailService, times(1)).sendNewMail(any(), any(), any());

        // Assert the response
        assertEquals("success", responseDTO.getStatus());
        assertNotNull(responseDTO.getMessage());
    }

    @Test
    public void testSaveGstLog_UserNotFound() throws MessagingException {
        // Mock dependencies
        when(userInfoService.findUserByEmail(any())).thenReturn(null);

        // Execute the method
        CreateGstLogDTO createGstLogDTO = new CreateGstLogDTO();
        GstLogResponseDTO responseDTO = gstLogService.saveGstLog(createGstLogDTO);

        // Verify that the necessary methods were called
        verify(userInfoService, times(1)).findUserByEmail(any());
        verify(gstLogMapper, never()).insertLog(any());
        verify(mailService, never()).sendNewMail(any(), any(), any());

        // Assert the response
        assertEquals("error", responseDTO.getStatus());
        assertNotNull(responseDTO.getMessage());
    }

    // Add similar tests for other methods (checkResetPasswordExpiration, checkNewPasswordAvailability, manageResetUserPassword)
}
