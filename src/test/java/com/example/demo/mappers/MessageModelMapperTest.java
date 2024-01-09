package com.example.demo.mappers;

import com.example.demo.entity.MessageModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest: Loads full application context for integration testing,
// @Transactional: Ensures each test is transactional, rolling back DB changes after the test
@SpringBootTest
@Transactional
class MessageModelMapperTest {

    @Autowired
    private MessageModelMapper messageModelMapper;

    @Test
    public void testGetAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId() {
        // Replace these with appropriate test values
        Integer subContractorId = 1;
        Integer serviceProviderId = 1;
        Integer subContractorStatusId = 1;
        Integer serviceProviderStatusId = 1;

        List<MessageModel> results = messageModelMapper.getAllMessageModelByStatusIdOrSubContractorIdOrServiceProviderId(
                subContractorId, serviceProviderId, subContractorStatusId, serviceProviderStatusId);

        // Assertions to verify the results
        assertNotNull(results);
    }
}