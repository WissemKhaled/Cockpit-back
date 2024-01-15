package com.example.demo.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.Contract;

public class ContractBuilderTest {

	@Test
	void testBuildJwtResponse() {
		// GIVEN
		int cId = 1;
		String cContractNumber = "XCML-1264";
		int cFkSubcontractorId = 2;
		int cFKserviceProviderId = 9;

		// WHEN
		Contract contractBuilder = new ContractBuilder()
				.withId(cId)
				.withContractNumber(cContractNumber)
				.withSubcontractorId(cFkSubcontractorId)
				.withServiceProviderId(cFKserviceProviderId)
				.build();


		// THEN
		assertThat(contractBuilder).isNotNull();
		assertThat(cId).isEqualTo(contractBuilder.getcId());
		assertThat(cContractNumber).isEqualTo(contractBuilder.getcContractNumber());
		assertThat(cFkSubcontractorId).isEqualTo(contractBuilder.getcFkSubcontractorId());
		assertThat(cFKserviceProviderId).isEqualTo(contractBuilder.getcFKserviceProviderId());
	}

}
