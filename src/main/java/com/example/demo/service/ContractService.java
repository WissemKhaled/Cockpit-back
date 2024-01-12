package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ContractDTO;
import com.example.demo.entity.Contract;
import com.example.demo.exception.DatabaseQueryFailureException;


/**
 * Created by Elimane Fofana on 2024.
 */
public interface ContractService {

    List<Contract> getContractsByServiceProviderId(Integer serviceProviderId);
    List<Contract> getContractsBySubcontractorId(Integer subContractorId);

    public int saveContract(ContractDTO contractDTO) throws DatabaseQueryFailureException;
}
