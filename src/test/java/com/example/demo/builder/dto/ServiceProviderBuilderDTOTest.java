package com.example.demo.builder.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.Status;

public class ServiceProviderBuilderDTOTest {
	
	@Test
	void testBuildServiceProviderWithSubcontractorAndStatus() {
	    // GIVEN
	    int spId = 1;
	    String spFirstName = "ServiceProviderFirstName";
	    String spName = "ServiceProviderName";
	    String spEmail = "Sp@email.com";
	    LocalDateTime spCreationDate = LocalDateTime.now().minusDays(1);
	    LocalDateTime spLastUpdateDate = LocalDateTime.now();
	    String subcontractorName = "SubcontractorName";
	    Status spStatus = new Status(1, "En cours");

	    // WHEN
	    ServiceProviderDto serviceProvider = new ServiceProviderDtoBuilder()
	            .withSpId(spId)
	            .withSpFirstName(spFirstName)
	            .withSpName(spName)
	            .withSpEmail(spEmail)
	            .withSpCreationDate(spCreationDate)
	            .withSpLastUpdateDate(spLastUpdateDate)
	            .withSubcontractorName(subcontractorName)
	            .withSpStatus(spStatus)
	            .build();

	    // THEN
	    assertThat(serviceProvider).isNotNull();
	    assertThat(spId).isEqualTo(serviceProvider.getSpId());
	    assertThat(spName).isEqualTo(serviceProvider.getSpName());
	    assertThat(spFirstName).isEqualTo(serviceProvider.getSpFirstName());
	    assertThat(spCreationDate).isEqualTo(serviceProvider.getSpCreationDate());
	    assertThat(spLastUpdateDate).isEqualTo(serviceProvider.getSpLastUpdateDate());
	    assertThat(subcontractorName).isEqualTo(serviceProvider.getSubcontractorSName());
	    assertThat(spStatus).isEqualTo(serviceProvider.getSpStatus());
	}

}
