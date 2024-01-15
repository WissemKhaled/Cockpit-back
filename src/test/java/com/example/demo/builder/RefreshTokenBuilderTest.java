package com.example.demo.builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UUser;

public class RefreshTokenBuilderTest {
	
	@Test
	void testBuildRefreshTokenWithValidData() {
		// GIVEN
		
	    int rtId = 1;
	    String rtToken = "sampleToken";
	    Instant rtExpiryDate = Instant.now().plusSeconds(3600);
	    UUser uUser = new UUser();
	    uUser.setUId(1);
	    uUser.setUEmail("john@test.fr");
	    uUser.setUPassword("123-Az");
	    uUser.setUFirstName("John");
	    uUser.setULastName("Doe");
	    uUser.setUStatus(true);
	    uUser.setUInsertionDate(LocalDateTime.now().minusDays(1));
	    uUser.setULastUpdate(LocalDateTime.now());
	    

		 // WHEN
	    RefreshToken refreshToken = new RefreshTokenBuilder()
	            .withRtId(rtId)
	            .withRtToken(rtToken)
	            .withRtExpiryDate(rtExpiryDate)
	            .withUUser(uUser)
	            .build();
	    		
	    
	 // THEN
	    assertThat(refreshToken).isNotNull();
	    assertThat(rtId).isEqualTo(refreshToken.getRtId());
	    assertThat(rtToken).isEqualTo(refreshToken.getRtToken());
	    assertThat(rtExpiryDate).isEqualTo(refreshToken.getRtExpiryDate());
	    assertThat(uUser).isEqualTo(refreshToken.getUUser());	}
}
