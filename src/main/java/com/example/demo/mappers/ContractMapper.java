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

    @Select("SELECT DISTINCT gc.c_contract_number\n" +
            "FROM gst_contract gc\n" +
            "         INNER JOIN gst_service_provider gsp ON gc.c_fk_service_provider_id = gsp.sp_id\n" +
            "         INNER JOIN gst_subcontractor gsc ON gc.c_fk_service_provider_id = gsc.s_id\n" +
            "         INNER JOIN gst_model_tracking gmt ON gmt.mt_fk_contract_id = gc.c_id\n" +
            "         INNER JOIN gst_message_model gmm ON gmm.mm_id = gmt.mt_fk_message_model_id\n" +
            "WHERE (gsp.sp_id IS NULL OR gsp.sp_id = #{serviceProviderId})\n" +
            "   OR (gsc.s_id IS NULL OR gsc.s_id = #{subContractorId})\n" +
            "   OR (gmm.mm_id IS NULL OR gmm.mm_id = #{messageModelId});")
    @Result(property = "cId", column = "c_id")
    @Result(property = "cContractNumber", column = "c_contract_number")
    List<Contract> getContractsByMessageModelId(@Param("serviceProviderId") Integer serviceProviderId, @Param("subContractorId") Integer subContractorId, @Param("messageModelId") Integer messageModelId);



	@Insert("INSERT INTO schema_dev.gst_contract (c_contract_number, c_fk_subcontractor_id, c_fk_service_provider_id) " +
            "VALUES (#{cContractNumber}, #{cFkSubcontractorId}, #{cFKserviceProviderId})")
	@Options(useGeneratedKeys = true, keyProperty = "cId", keyColumn = "c_id")
	int insertContract(Contract contract);
}
