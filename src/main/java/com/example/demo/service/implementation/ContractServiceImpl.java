package com.example.demo.service.implementation;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Contract;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.ContractMapper;
import com.example.demo.service.ContractService;

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
    public List<Contract> getContractsByMessageModelId(Integer serviceProviderId, Integer subContractorId) {
        List<Contract> contracts = contractMapper.getContractsByMessageModelId(subContractorId,serviceProviderId);
        return Optional.ofNullable(contracts).filter(not(List::isEmpty))
                .orElseThrow(() -> new MessageModelNotFoundException("No contract exists for this id!"));
    }

}
