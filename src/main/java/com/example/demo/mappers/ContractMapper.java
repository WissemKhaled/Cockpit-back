package com.example.demo.mappers;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Contract;

/**
 * Created by Elimane Fofana on 2024.
 */
@Mapper
public interface ContractMapper {
	Contract getContractByContractId(Integer contractId);
	
    List<Contract> getContractsByServiceProviderId(Integer serviceProviderId);

	int insertContract(Contract contract);

    List<Contract> getContractsBySubcontractorId(Integer subContractorId);
    
    int updateContractByContractId(Contract contract);
}
