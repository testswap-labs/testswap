package com.testswap.api.controller;

import com.testswap.api.dto.TraceDtos;
import com.testswap.api.service.TraceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trace")
public class TraceController {

    private final TraceService traceService;

    public TraceController(TraceService traceService) {
        this.traceService = traceService;
    }

    @PostMapping("/tx")
    public ResponseEntity<TraceDtos.Response> traceTx(@RequestBody TraceDtos.TxRequest req) {
        return ResponseEntity.ok(traceService.traceTx(req));
    }
}
