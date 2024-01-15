package com.example.demo.builder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

public class SubcontractorBuilderTest {
	
	 	@Test
	    void testBuildSubcontractor() {
	        
	 		// GIVEN
	        int sId = 1;
	        String sName = "SubcontractorName";
	        String sEmail = "subcontractor@example.com";
	        LocalDateTime sCreationDate = LocalDateTime.now().minusDays(1);
	        LocalDateTime sLastUpdateDate = LocalDateTime.now();

	        Status status = new Status(1, "En cours");

	        ServiceProvider sp1 = new ServiceProvider();
	        sp1.setSpId(1);
	        sp1.setSpName("Provider1");
	        
	        ServiceProvider sp2 = new ServiceProvider();
	        sp1.setSpId(2);
	        sp1.setSpName("Provider2");
	        
	        List<ServiceProvider> serviceProviders = Arrays.asList(sp1, sp2);

	        // WHEN
	        Subcontractor subcontractor = new SubcontractorBuilder()
	                .withSId(sId)
	                .withSName(sName)
	                .withSEmail(sEmail)
	                .withSCreationDate(sCreationDate)
	                .withSLastUpdateDate(sLastUpdateDate)
	                .withStatus(status)
	                .withServiceProviders(serviceProviders)
	                .build();

	        // THEN
	        assertThat(subcontractor).isNotNull();
	        assertThat(sId).isEqualTo(subcontractor.getSId());
	        assertThat(sName).isEqualTo(subcontractor.getSName());
	        assertThat(sEmail).isEqualTo(subcontractor.getSEmail());
	        assertThat(sCreationDate).isEqualTo(subcontractor.getSCreationDate());
	        assertThat(sLastUpdateDate).isEqualTo(subcontractor.getSLastUpdateDate());
	        assertThat(status).isEqualTo(subcontractor.getStatus());
	        assertThat(sId).isEqualTo(subcontractor.getSId());
	        assertThat(serviceProviders).isEqualTo(subcontractor.getServiceProviders());
	    }
	 	
}
