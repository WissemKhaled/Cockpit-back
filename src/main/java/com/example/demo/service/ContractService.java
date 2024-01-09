package com.example.demo.service;

import com.example.demo.entity.Contract;

import java.util.List;

/**
 * Created by Elimane Fofana on 2024.
 */
public interface ContractService {

    List<Contract> getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId(Integer serviceProviderId, Integer subContractorId, Integer messageModelId);

}
