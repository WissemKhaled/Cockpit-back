package com.example.demo.mappers;

import com.example.demo.entity.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Elimane Fofana on 2024.
 */
@Mapper
public interface ContractMapper {


    @Select("SELECT DISTINCT c_id, gc.c_contract_number\n" +
            "FROM gst_contract gc\n" +
            "INNER JOIN gst_service_provider gsp ON gc.c_fk_service_provider_id = gsp.sp_id\n" +
            "INNER JOIN gst_subcontractor gsc ON gc.c_fk_service_provider_id = gsc.s_id\n" +
            "WHERE (gsp.sp_id IS NULL OR gsp.sp_id = #{serviceProviderId})\n" +
            "   OR (gsc.s_id IS NULL OR gsc.s_id = #{subContractorId});")
    @Result(property = "cId", column = "c_id")
    @Result(property = "cContractNumber", column = "c_contract_number")
    List<Contract> getContractsByMessageModelId(@Param("serviceProviderId") Integer serviceProviderId, @Param("subContractorId") Integer subContractorId);


}
