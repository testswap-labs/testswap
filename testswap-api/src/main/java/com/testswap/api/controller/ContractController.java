package com.testswap.api.controller;

import com.testswap.api.dto.ContractDtos;
import com.testswap.api.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/call")
    public ResponseEntity<ContractDtos.GenericResponse> call(@RequestBody ContractDtos.CallRequest req) {
        return ResponseEntity.ok(contractService.call(req));
    }

    @PostMapping("/send")
    public ResponseEntity<ContractDtos.GenericResponse> send(@RequestBody ContractDtos.SendRequest req) {
        return ResponseEntity.ok(contractService.send(req));
    }

    @PostMapping("/deploy")
    public ResponseEntity<ContractDtos.GenericResponse> deploy(@RequestBody ContractDtos.DeployRequest req) {
        return ResponseEntity.ok(contractService.deploy(req));
    }
}
