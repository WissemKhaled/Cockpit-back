package com.example.demo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.example.demo.entity.Contract;

@Mapper
public interface ContractMapper {
	
	@Insert("INSERT INTO schema_dev.gst_contract (c_contract_number, c_fk_subcontractor_id, c_fk_service_provider_id) " +
            "VALUES (#{cContractNumber}, #{cFkSubcontractorId}, #{cFKserviceProviderId})")
	@Options(useGeneratedKeys = true, keyProperty = "cId", keyColumn = "c_id")
	int insertContract(Contract contract);
}
