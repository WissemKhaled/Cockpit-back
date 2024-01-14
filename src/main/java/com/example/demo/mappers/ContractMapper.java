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

    @Select("SELECT DISTINCT c_id, gc.c_contract_number" +
            "FROM gst_contract gc" +
            "INNER JOIN gst_service_provider gsp ON gc.c_fk_service_provider_id = gsp.sp_id" +
            "INNER JOIN gst_subcontractor gsc ON gc.c_fk_service_provider_id = gsc.s_id" +
            "WHERE (gsp.sp_id IS NULL OR gsp.sp_id = #{serviceProviderId})")

    @Result(property = "cId", column = "c_id")
    @Result(property = "cContractNumber", column = "c_contract_number")
    List<Contract> getContractsByServiceProviderId(@Param("serviceProviderId") Integer serviceProviderId);
    
	@Insert("INSERT INTO schema_dev.gst_contract (c_contract_number, c_fk_subcontractor_id, c_fk_service_provider_id) " +
            "VALUES (#{cContractNumber}, #{cFkSubcontractorId}, #{cFKserviceProviderId})")
	@Options(useGeneratedKeys = true, keyProperty = "cId", keyColumn = "c_id")
	int insertContract(Contract contract);
	
	@Select("SELECT DISTINCT c_id, gc.c_contract_number\n" +
            "FROM gst_contract gc\n" +
            "INNER JOIN gst_service_provider gsp ON gc.c_fk_service_provider_id = gsp.sp_id\n" +
            "INNER JOIN gst_subcontractor gsc ON gc.c_fk_service_provider_id = gsc.s_id\n" +
            "WHERE (gsc.s_id IS NULL OR gsc.s_id = #{subContractorId});")
    @Result(property = "cId", column = "c_id")
    @Result(property = "cContractNumber", column = "c_contract_number")
    List<Contract> getContractsBySubcontractorId(@Param("subContractorId") Integer subContractorId);
}
