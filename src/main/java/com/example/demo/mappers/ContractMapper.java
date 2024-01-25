package com.example.demo.mappers;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Contract;

/**
 * Created by Elimane Fofana on 2024.
 */
@Mapper
public interface ContractMapper {

    List<Contract> getContractsByServiceProviderId(Integer serviceProviderId);

	int insertContract(Contract contract);

    List<Contract> getContractsBySubcontractorId(Integer subContractorId);

}
