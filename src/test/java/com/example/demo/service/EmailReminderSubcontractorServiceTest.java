package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.EmailReminderMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.implementation.EmailReminderSubcontractorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmailReminderSubcontractorServiceTest {

    @Mock
    private SubcontractorMapper subcontractorMapper;

    @Mock
    private EmailReminderMapper emailReminderMapper;

    @InjectMocks
    private EmailReminderSubcontractorServiceImpl emailReminderSubcontractorService;

    @Test
    public void testGetSubcontractorReminderInfoBySId() {
        int subcontractorId = 1;
        Subcontractor foundSubcontractor = new Subcontractor();
        when(subcontractorMapper.findSubcontractorWithStatusById(subcontractorId)).thenReturn(foundSubcontractor);

        List<GstStatusModelSubcontractorDTO> expectedResult = Collections.singletonList(new GstStatusModelSubcontractorDTO());
        when(emailReminderMapper.findSubcontractorReminderInfo(subcontractorId)).thenReturn(expectedResult);

        List<GstStatusModelSubcontractorDTO> result = emailReminderSubcontractorService.getSubcontractorReminderInfoBySId(subcontractorId);

        assertNotNull(result);
        assertEquals(expectedResult, result);
        verify(subcontractorMapper, times(1)).findSubcontractorWithStatusById(subcontractorId);
        verify(emailReminderMapper, times(1)).findSubcontractorReminderInfo(subcontractorId);
    }

    @Test
    public void testGetSubcontractorReminderInfoBySIdWhenSubcontractorNotFound() {
        int subcontractorId = 1;
        when(subcontractorMapper.findSubcontractorWithStatusById(subcontractorId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class,
                () -> emailReminderSubcontractorService.getSubcontractorReminderInfoBySId(subcontractorId),
                "Aucun sous-traitant trouvé avec l'id " + subcontractorId);

        verify(subcontractorMapper, times(1)).findSubcontractorWithStatusById(subcontractorId);
        verifyNoInteractions(emailReminderMapper);
    }
    
    @Test
    public void testGetSubcontractorReminderInfoBySpIdAndMmId() {
        int sId = 1;
        int mmId = 2;
        GstStatusModelSubcontractorDTO expectedResult = new GstStatusModelSubcontractorDTO();
        when(emailReminderMapper.findAlertBySubcontractorIdAndMmId(sId, mmId)).thenReturn(expectedResult);

        GstStatusModelSubcontractorDTO result = emailReminderSubcontractorService.getSubcontractorReminderInfoBySpIdAndMmId(sId, mmId);

        assertEquals(expectedResult, result);
        verify(emailReminderMapper, times(1)).findAlertBySubcontractorIdAndMmId(sId, mmId);
    }

    @Test
    public void testGetSubcontractorReminderInfoBySpIdAndMmIdWhenNotFound() {
        int sId = 1;
        int mmId = 2;
        when(emailReminderMapper.findAlertBySubcontractorIdAndMmId(sId, mmId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            emailReminderSubcontractorService.getSubcontractorReminderInfoBySpIdAndMmId(sId, mmId);
        });

        verify(emailReminderMapper, times(1)).findAlertBySubcontractorIdAndMmId(sId, mmId);
    }
}