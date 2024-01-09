package com.example.demo.mappers;

import com.example.demo.entity.Contract;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContractMapperTest {
    @Autowired
    private ContractMapper contractMapper;

    @Test
    public void getContractsByMessageModelId() {
        // Replace these with appropriate test values
        Integer subContractorId = 1;
        Integer serviceProviderId = 1;
        Integer messageModelId = 1;

        List<Contract> results = contractMapper.getContractsByMessageModelId(serviceProviderId, subContractorId, messageModelId);

        // Assertions to verify the results
        assertNotNull(results);
        // add more assertions here to validate the data according to your requirements
    }
}

