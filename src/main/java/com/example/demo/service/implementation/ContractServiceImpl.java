package com.example.demo.service.implementation;

import com.example.demo.entity.Contract;
import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.ContractMapper;
import com.example.demo.mappers.MessageModelMapper;
import com.example.demo.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

/**
 * Created by Elimane Fofana on 2024.
 */
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractMapper mapper) {
        this.contractMapper = mapper;
    }

    @Override
    public List<Contract> getContractsBySubContractorIdOrServiceProviderIdOrMessageModelId(Integer serviceProviderId, Integer subContractorId, Integer messageModelId) {
        List<Contract> contracts = contractMapper.getContractsByMessageModelId(serviceProviderId,subContractorId,messageModelId);
        return Optional.ofNullable(contracts).filter(not(List::isEmpty))
                .orElseThrow(() -> new MessageModelNotFoundException("No contract exists for this id!"));
    }

}
