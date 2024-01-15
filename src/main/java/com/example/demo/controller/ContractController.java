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

    @GetMapping("/getContractsByServiceProviderId")
    public ResponseEntity<Page<Contract>> getContractsByServiceProviderId (
            @RequestParam(value = "serviceProviderId", required = false) Integer serviceProviderId,
            @PageableDefault(page = 0, size = 6) Pageable pageable) {

        try {
            List<Contract> contractList = contractService.getContractsByServiceProviderId(serviceProviderId);

            Page<Contract> page = new PageImpl<>(contractList, pageable, contractList.size());

            return ResponseEntity.ok(page);

        } catch (EntityNotFoundException e) {
            return (ResponseEntity<Page<Contract>>) ResponseEntity.notFound();
        }
    }
    
    @GetMapping("/getContractsBySubcontractorId")
    public ResponseEntity<Page<Contract>> getContractsBySubcontractorId (
            @RequestParam(value = "subcontractorId", required = false) Integer subcontractorId,
            @PageableDefault(page = 0, size = 6) Pageable pageable) {

        try {
            List<Contract> contractList = contractService.getContractsBySubcontractorId(subcontractorId);

            Page<Contract> page = new PageImpl<>(contractList, pageable, contractList.size());

            return ResponseEntity.ok(page);

        } catch (EntityNotFoundException e) {
            return (ResponseEntity<Page<Contract>>) ResponseEntity.notFound();
        }
    }

}
