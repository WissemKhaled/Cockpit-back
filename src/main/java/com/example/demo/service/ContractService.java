package com.example.demo.service;

import com.example.demo.dto.ContractDTO;
import com.example.demo.exception.DatabaseQueryFailureException;

public interface ContractService {
	public int saveContract(ContractDTO contractDTO) throws DatabaseQueryFailureException;
}
