package com.example.demo.mappers;

import com.example.demo.entity.MessageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageModelMapperTest {

    @Autowired
    private MessageModelMapper messageModelMapper;

    @Test
    public void testGetAllMessageModelBySubContractor() {
        // Given
        Integer subContractorStatusId = 1;

        // When
        List<MessageModel> results = messageModelMapper.getAllMessageModelBySubcontractorId(subContractorStatusId);

        // Then
        assertNotNull(results);
        //assertFalse(results.isEmpty()); // Assuming you're expecting non-empty results
    }

    @Test
    public void testGetAllMessageModelByServiceProvider() {
        // Given
        Integer serviceProviderId = 1;

        // When
        List<MessageModel> results = messageModelMapper.getAllMessageModelByServiceProviderId(serviceProviderId);

        // Then
        assertNotNull(results);
        //assertFalse(results.isEmpty()); // Assuming you're expecting non-empty results
    }

    @Test
    public void testGetAllMessageModelBySubContractor_Should_Return_Null_Value() {
        // Given
        Integer subContractorStatusId = null;

        // When
        List<MessageModel> results = messageModelMapper.getAllMessageModelBySubcontractorId(subContractorStatusId);

        // Then
        assertEquals(results.isEmpty(), true);
        //assertFalse(results.isEmpty()); // Assuming you're expecting non-empty results
    }

    @Test
    public void testGetAllMessageModelByServiceProvider_Should_Return_Null_Value() {
        // Given
        Integer serviceProviderId = null;

        // When
        List<MessageModel> results = messageModelMapper.getAllMessageModelByServiceProviderId(serviceProviderId);

        // Then
        assertEquals(results.isEmpty(), true);
        //assertFalse(results.isEmpty()); // Assuming you're expecting non-empty results
    }
}