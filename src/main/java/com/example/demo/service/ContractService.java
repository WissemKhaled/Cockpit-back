package com.example.demo.service;

import com.example.demo.dto.ContractDTO;
import com.example.demo.entity.Contract;
import com.example.demo.exception.DatabaseQueryFailureException;

import java.util.List;

/**
 * Created by Elimane Fofana on 2024.
 */
public interface ContractService {

    List<Contract> getContractsByMessageModelId(Integer serviceProviderId,Integer subContractorId,Integer messageModelId);

    public int saveContract(ContractDTO contractDTO) throws DatabaseQueryFailureException;
}
