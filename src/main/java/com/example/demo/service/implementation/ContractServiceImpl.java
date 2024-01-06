package com.example.demo.service.implementation;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ContractDTO;
import com.example.demo.dto.mapper.ContractDtoMapper;
import com.example.demo.entity.Contract;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.mappers.ContractMapper;
import com.example.demo.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	private final ContractDtoMapper contractDtoMapper;
	private final ContractMapper contractMapper;
	
	private static final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	public ContractServiceImpl(ContractDtoMapper contractDtoMapper, ContractMapper contractMapper) {
		this.contractDtoMapper = contractDtoMapper;
		this.contractMapper = contractMapper;
	}
	
	@Override
	public int saveContract(ContractDTO contractDTO) throws DatabaseQueryFailureException {
		try {
			if (contractDTO == null) {
				log.error("Le paramètre contractDto ne peut être null");
				throw new IllegalArgumentException("Le paramètre contractDto ne peut être null");
			}
			
			contractDTO.setcContractNumber(generateRandomContractNumber());
			
			Contract contract = contractDtoMapper.toContract(contractDTO);
			
			int isContractInserted = this.contractMapper.insertContract(contract);
			
			if (isContractInserted == 0) {
				log.error("Échec de l'insertion du contrat dans la base de données");
	            throw new DatabaseQueryFailureException("Échec de l'insertion du contrat dans la base de données");
			}
			
			log.info("Contrat créé avec succès avec l'id = " + contract.getcId());
	        return contract.getcId();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (DatabaseQueryFailureException e) {
			throw e;
		}
	}
	
	public static String generateRandomContractNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 13).toUpperCase();
    }
}
