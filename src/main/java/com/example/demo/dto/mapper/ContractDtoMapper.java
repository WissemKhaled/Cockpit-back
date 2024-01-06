package com.example.demo.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.ContractDTO;
import com.example.demo.entity.Contract;

@Component
public class ContractDtoMapper {
	public ContractDTO toDto(Contract contract) {
		return new ContractDTO(contract.getcId(), contract.getcContractNumber(), contract.getcFkSubcontractorId(),
				contract.getcFKserviceProviderId());
	}
	
	public Contract toContract(ContractDTO contractDTO) {
		return new Contract(contractDTO.getcId(), contractDTO.getcContractNumber(), contractDTO.getcFkSubcontractorId(),
				contractDTO.getcFKserviceProviderId());
	}
}
