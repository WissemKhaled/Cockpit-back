package com.example.demo.builder.dto;

import com.example.demo.dto.ContractDTO;

public class ContractBuilderDTO {
	private int cId;
	private String cContractNumber;
	private int cFkSubcontractorId;
	private int cFKserviceProviderId; 

	public ContractBuilderDTO withId(int cId) {
		this.cId = cId;
		return this;
	}

	public ContractBuilderDTO withContractNumber(String cContractNumber) {
		this.cContractNumber = cContractNumber;
		return this;
	}

	public ContractBuilderDTO withSubcontractorId(int cFkSubcontractorId) {
		this.cFkSubcontractorId = cFkSubcontractorId;
		return this;
	}

	public ContractBuilderDTO withServiceProviderId(int cFKserviceProviderId) {
		this.cFKserviceProviderId = cFKserviceProviderId;
		return this;
	}

	public ContractDTO build() {
		return new ContractDTO(cId, cContractNumber, cFkSubcontractorId, cFKserviceProviderId);
	}
}
