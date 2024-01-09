package com.example.demo.controller;

import com.example.demo.entity.Contract;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Elimane Fofana on 2024.
 */
@RestController
@RequestMapping("/contract")
@CrossOrigin(origins = "http://localhost:4200")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/getContractsById")
    public ResponseEntity<Page<Contract>> getAllMessageModelsAndStatusForSubcontractorCategory(
            @RequestParam(value = "serviceProviderId", required = false) Integer serviceProviderId,
            @RequestParam(value = "subContractorId", required = false) Integer subContractorId,
            @PageableDefault(page = 0, size = 6) Pageable pageable) {

        try {
            List<Contract> contractList = contractService.getContractsByMessageModelId(serviceProviderId,subContractorId);

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), contractList.size());
            List<Contract> contracts = contractList.subList(start, end);

            Page<Contract> page = new PageImpl<>(contracts, pageable, contractList.size());

            return ResponseEntity.ok(page);

        } catch (EntityNotFoundException e) {
            return (ResponseEntity<Page<Contract>>) ResponseEntity.notFound();
        }
    }

}
