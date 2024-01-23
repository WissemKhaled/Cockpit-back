package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Contract;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.ContractService;

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


    @GetMapping("/getContractsByServiceProviderId")
    public ResponseEntity<?> getContractsByServiceProviderId (
            @RequestParam(value = "serviceProviderId", required = false) Integer serviceProviderId) {
    	

        try {
            List<Contract> contractList = contractService.getContractsByServiceProviderId(serviceProviderId);

//            Page<Contract> page = new PageImpl<>(contractList, pageable, contractList.size());

            return ResponseEntity.ok(contractList);

        } catch (EntityNotFoundException e) {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }
    
    @GetMapping("/getContractsBySubcontractorId")
    public ResponseEntity<?> getContractsBySubcontractorId (
            @RequestParam(value = "subContractorId", required = false) Integer subcontractorId) {

        try {
            List<Contract> contractList = contractService.getContractsBySubcontractorId(subcontractorId);

//            Page<Contract> page = new PageImpl<>(contractList, pageable, contractList.size());

            return ResponseEntity.ok(contractList);

        } catch (EntityNotFoundException e) {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

}
