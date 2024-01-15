package com.example.demo.builder.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.ContractDTO;

public class ContractBuilderDTOTest {

	@Test
	void testBuildJwtResponse() {
		// GIVEN
		int cId = 1;
		String cContractNumber = "XCML-1264";
		int cFkSubcontractorId = 2;
		int cFKserviceProviderId = 9;

		// WHEN
		ContractDTO contractBuilderDTO = new ContractBuilderDTO()
				.withId(cId)
				.withContractNumber(cContractNumber)
				.withSubcontractorId(cFkSubcontractorId)
				.withServiceProviderId(cFKserviceProviderId)
				.build();


		// THEN
		assertThat(contractBuilderDTO).isNotNull();
		assertThat(cId).isEqualTo(contractBuilderDTO.getcId());
		assertThat(cContractNumber).isEqualTo(contractBuilderDTO.getcContractNumber());
		assertThat(cFkSubcontractorId).isEqualTo(contractBuilderDTO.getcFkSubcontractorId());
		assertThat(cFKserviceProviderId).isEqualTo(contractBuilderDTO.getcFKserviceProviderId());
	}

}
