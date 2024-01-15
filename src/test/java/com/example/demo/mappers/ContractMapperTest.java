package com.example.demo.mappers;

import com.example.demo.entity.Contract;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Elimane Fofana on 2024.
 */

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test") // Make sure to have a test profile that configures an in-memory database
@Sql(scripts = {"/schema-test-h2.sql", "/data-test-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // Adjust paths to your schema and data scripts
class ContractMapperTest {
    @Autowired
    private ContractMapper contractMapper;

    @Test
    public void getContractsBySubContractorId() {
        // Replace these with appropriate test values
        Integer subContractorId = 1;


        List<Contract> results = contractMapper.getContractsBySubcontractorId(subContractorId);

        // Assertions to verify the results
        assertNotNull(results);
        // add more assertions here to validate the data according to your requirements
    }

    @Test
    public void getContractsByServiceProviderId() {
        // Replace these with appropriate test values
        Integer serviceProviderId = 1;

        List<Contract> results = contractMapper.getContractsByServiceProviderId(serviceProviderId);

        // Assertions to verify the results
        assertNotNull(results);
        // add more assertions here to validate the data according to your requirements
    }
}

