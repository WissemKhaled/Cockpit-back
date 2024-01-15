package com.example.demo.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.Status;

public class StatusBuilderTest {
	
	@Test
	void testBuildStatus() {
	    // GIVEN
	    int stId = 1;
	    String stName = "En cours";


	    // WHEN
	    Status status = new StatusBuilder()
	            .withStId(stId)
	            .withStName(stName)
	            .build();

	    // THEN
	    assertThat(status).isNotNull();
	    assertThat(stId).isEqualTo(status.getStId());
	    assertThat(stName).isEqualTo(status.getStName());
	}

}
