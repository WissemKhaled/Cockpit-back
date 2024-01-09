package com.example.demo.service;

import com.example.demo.entity.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Elimane Fofana on 2024.
 */
public interface ContractService {

    List<Contract> getContractsByMessageModelId(Integer serviceProviderId,Integer subContractorId,Integer messageModelId);

}
