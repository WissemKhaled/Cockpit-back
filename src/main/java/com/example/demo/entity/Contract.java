package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contract {
	@JsonProperty("cId")
	private int cId;
	
	@JsonProperty("cContractNumber")
	private String cContractNumber;
	
	@JsonProperty("cFkSubcontractorId")
	private int cFkSubcontractorId;
	
	@JsonProperty("cFkServiceProviderId")
	private int cFKserviceProviderId;

	public Contract() {
	}

	public Contract(int cId, String cContractNumber, int cFkSubcontractorId, int cFKserviceProviderId) {
		this.cId = cId;
		this.cContractNumber = cContractNumber;
		this.cFkSubcontractorId = cFkSubcontractorId;
		this.cFKserviceProviderId = cFKserviceProviderId;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcContractNumber() {
		return cContractNumber;
	}

	public void setcContractNumber(String cContractNumber) {
		this.cContractNumber = cContractNumber;
	}

	public int getcFkSubcontractorId() {
		return cFkSubcontractorId;
	}

	public void setcFkSubcontractorId(int cFkSubcontractorId) {
		this.cFkSubcontractorId = cFkSubcontractorId;
	}

	public int getcFKserviceProviderId() {
		return cFKserviceProviderId;
	}

	public void setcFKserviceProviderId(int cFKserviceProviderId) {
		this.cFKserviceProviderId = cFKserviceProviderId;
	}

	@Override
	public String toString() {
		return "Contract [cId=" + cId + ", cContractNumber=" + cContractNumber + ", cFkSubcontractorId="
				+ cFkSubcontractorId + ", cFKserviceProviderId=" + cFKserviceProviderId + "]";
	}

}
