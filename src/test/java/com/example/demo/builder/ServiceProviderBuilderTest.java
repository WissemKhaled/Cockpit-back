package com.example.demo.builder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

public class ServiceProviderBuilderTest {
	@Test
	public void testBuildServiceProviderWithSubcontractorAndStatus() {
	    // GIVEN
	    int spId = 1;
	    String spFirstName = "ServiceProviderFirstName";
	    String spName = "ServiceProviderName";
	    String spEmail = "Sp@email.com";
	    LocalDateTime spCreationDate = LocalDateTime.now().minusDays(1);
	    LocalDateTime spLastUpdateDate = LocalDateTime.now();
	    Subcontractor subcontractor = new SubcontractorBuilder()
	    		.withSId(1)
	    		.withSName("SubcontractorName")
	    		.withSEmail("SubName@email.fr")
	    		.build();
	    
	    Status spStatus = new Status(1, "En cours");

	    // WHEN
	    ServiceProvider serviceProvider = new ServiceProviderBuilder()
	            .withSpId(spId)
	            .withSpFirstName(spFirstName)
	            .withSpName(spName)
	            .withSpEmail(spEmail)
	            .withSpCreationDate(spCreationDate)
	            .withSpLastUpdateDate(spLastUpdateDate)
	            .withSubcontractor(subcontractor)
	            .withSpStatus(spStatus)
	            .build();

	    // THEN
	    assertThat(serviceProvider).isNotNull();
	    assertThat(spId).isEqualTo(serviceProvider.getSpId());
	    assertThat(spName).isEqualTo(serviceProvider.getSpName());
	    assertThat(spFirstName).isEqualTo(serviceProvider.getSpFirstName());
	    assertThat(spCreationDate).isEqualTo(serviceProvider.getSpCreationDate());
	    assertThat(spLastUpdateDate).isEqualTo(serviceProvider.getSpLastUpdateDate());
	    assertThat(subcontractor).isEqualTo(serviceProvider.getSubcontractor());
	    assertThat(spStatus).isEqualTo(serviceProvider.getSpStatus());
	}

}
