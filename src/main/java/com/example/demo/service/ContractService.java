package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Contract;

/**
 * Created by Elimane Fofana on 2024.
 */
public interface ContractService {

    List<Contract> getContractsByMessageModelId(Integer serviceProviderId,Integer subContractorId);

}
