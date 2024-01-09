package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.ModelTrackingDTO;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.ModelTrackingMapper;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.implementation.ModelTrackingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ModelTrackingServiceTest {

    @Mock
    private ModelTrackingMapper emailReminderMapper;

    @Mock
    private ServiceProviderMapper serviceProviderMapper;

    @InjectMocks
    private ModelTrackingServiceImpl emailReminderServiceProviderService;

//    @Test
//    public void testGetServiceProviderReminderInfoBySpId() {
//        int serviceProviderId = 1;
//        ServiceProvider serviceProvider = new ServiceProvider();
//        when(serviceProviderMapper.findServiceProviderById(serviceProviderId)).thenReturn(serviceProvider);
//
//        List<ModelTrackingDTO> expectedResult = new ArrayList<>();
//        when(emailReminderMapper.findServiceProviderReminderInfo(serviceProviderId)).thenReturn(expectedResult);
//
//        List<ModelTrackingDTO> result = emailReminderServiceProviderService.getServiceProviderReminderInfoBySpId(serviceProviderId);
//
//        assertEquals(expectedResult, result);
//        verify(serviceProviderMapper, times(1)).findServiceProviderById(serviceProviderId);
//        verify(emailReminderMapper, times(1)).findServiceProviderReminderInfo(serviceProviderId);
//    }
//
//    @Test
//    public void testGetServiceProviderReminderInfoBySpIdWhenNotFound() {
//        int serviceProviderId = 1;
//        when(serviceProviderMapper.findServiceProviderById(serviceProviderId)).thenReturn(null);
//
//        assertThrows(EntityNotFoundException.class, () -> {
//            emailReminderServiceProviderService.getServiceProviderReminderInfoBySpId(serviceProviderId);
//        });
//
//        verify(serviceProviderMapper, times(1)).findServiceProviderById(serviceProviderId);
//    }
//
//    @Test
//    public void testGetSpReminderInfoBySpIdAndMmId() {
//        int serviceProviderId = 1;
//        int mmId = 2;
//        ModelTrackingDTO expectedResult = new ModelTrackingDTO();
//        when(emailReminderMapper.findAlertByServiceProviderIdAndMmId(serviceProviderId, mmId)).thenReturn(expectedResult);
//
//        ModelTrackingDTO result = emailReminderServiceProviderService.getSpReminderInfoBySpIdAndMmId(serviceProviderId, mmId);
//
//        assertEquals(expectedResult, result);
//        verify(emailReminderMapper, times(1)).findAlertByServiceProviderIdAndMmId(serviceProviderId, mmId);
//    }
//
//    @Test
//    public void testGetSpReminderInfoBySpIdAndMmIdWhenNotFound() {
//
//        int serviceProviderId = 1;
//        int mmId = 2;
//        when(emailReminderMapper.findAlertByServiceProviderIdAndMmId(serviceProviderId, mmId)).thenReturn(null);
//
//        assertThrows(EntityNotFoundException.class, () -> {
//            emailReminderServiceProviderService.getSpReminderInfoBySpIdAndMmId(serviceProviderId, mmId);
//        });
//
//        verify(emailReminderMapper, times(1)).findAlertByServiceProviderIdAndMmId(serviceProviderId, mmId);
//    }
}