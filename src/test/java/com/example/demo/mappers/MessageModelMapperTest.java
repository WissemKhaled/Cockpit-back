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

// @SpringBootTest: Loads full application context for integration testing,
// @Transactional: Ensures each test is transactional, rolling back DB changes after the test
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test") // Make sure to have a test profile that configures an in-memory database
@Sql(scripts = {"/schema-test-h2.sql", "/data-test-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // Adjust paths to your schema and data scripts
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
}