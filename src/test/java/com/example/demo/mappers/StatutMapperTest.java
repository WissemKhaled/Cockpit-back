package com.example.demo.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Status;

@SpringBootTest
@Transactional
public class StatutMapperTest {

	@Autowired
	private StatusMapper statusMapper;

	@Test
	void testFindStatusById_ValidId_ShouldReturnStatus() {
		// GIVEN
		int statusId = 1; 

		// WHEN
		Status status = statusMapper.findStatusById(statusId);

		// THEN
		assertNotNull(status);
		assertThat(status).isNotNull();
		assertThat(status.getStId()).isEqualTo(1);
		assertThat(status.getStName()).isNotNull().isEqualTo("En cours");
	}

	@Test
	void testFindStatusById_InvalidId_ShouldReturnNull() {
		// GIVEN
		int invalidStatusId = -1; 
		
		// WHEN
		Status status = statusMapper.findStatusById(invalidStatusId);

		// THEN
		assertThat(status).isNull();
	}
	
    @Test
    public void testGetAllStatus_ShouldReturnNonEmptyList() {
        // GIVEN
    	// WHEN
        List<Status> allStatus = statusMapper.getAllStatus();

        // THEN
		assertThat(allStatus).isNotNull().isNotEmpty().hasSize(4);
    }
}
