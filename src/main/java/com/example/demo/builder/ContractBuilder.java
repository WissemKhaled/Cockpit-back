package com.example.demo.builder;

import com.example.demo.entity.Contract;

public class ContractBuilder {
	private int cId;
	private String cContractNumber;
	private int cFkSubcontractorId;
	private int cFKserviceProviderId;

	public ContractBuilder withId(int cId) {
		this.cId = cId;
		return this;
	}

	public ContractBuilder withContractNumber(String cContractNumber) {
		this.cContractNumber = cContractNumber;
		return this;
	}

	public ContractBuilder withSubcontractorId(int cFkSubcontractorId) {
		this.cFkSubcontractorId = cFkSubcontractorId;
		return this;
	}

	public ContractBuilder withServiceProviderId(int cFKserviceProviderId) {
		this.cFKserviceProviderId = cFKserviceProviderId;
		return this;
	}

	public Contract build() {
		return new Contract(cId, cContractNumber, cFkSubcontractorId, cFKserviceProviderId);
	}

}
