package com.example.demo.builder.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.StatusDto;

public class StatusBuilderDTOTest {
	
	@Test
	void testBuildStatusDto() {
	    // GIVEN
	    int stId = 1;
	    String stName = "En cours";


	    // WHEN
	    StatusDto status = new StatusDtoBuilder()
	            .withStId(stId)
	            .withStName(stName)
	            .build();

	    // THEN
	    assertThat(status).isNotNull();
	    assertThat(stId).isEqualTo(status.getStId());
	    assertThat(stName).isEqualTo(status.getStName());
	}

}
