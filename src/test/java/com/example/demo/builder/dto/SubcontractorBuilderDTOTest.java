package com.example.demo.builder.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Status;

public class SubcontractorBuilderDTOTest {
	
	 	@Test
	    void testBuildSubcontractor() {
	        
	 		// GIVEN
	        int sId = 1;
	        String sName = "SubcontractorName";
	        String sEmail = "subcontractor@example.com";
	        LocalDateTime sCreationDate = LocalDateTime.now().minusDays(1);
	        LocalDateTime sLastUpdateDate = LocalDateTime.now();

	        Status status = new Status(1, "En cours");
	        
	        List<Integer> serviceProvidersIds = Arrays.asList(1, 2);

	        // WHEN
	        SubcontractorDto subcontractor = new SubcontractorDtoBuilder()
	                .withSId(sId)
	                .withSName(sName)
	                .withSEmail(sEmail)
	                .withSCreationDate(sCreationDate)
	                .withSLastUpdateDate(sLastUpdateDate)
	                .withStatus(status)
	                .withServiceProvidersIds(serviceProvidersIds)
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
	        assertThat(serviceProvidersIds).isEqualTo(subcontractor.getServiceProvidersIds());
	    }
	 	
}
