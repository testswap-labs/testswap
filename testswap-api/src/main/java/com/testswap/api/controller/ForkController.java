package com.testswap.api.controller;

import com.testswap.api.config.AppProperties;
import com.testswap.api.dto.ForkDtos;
import com.testswap.api.model.ForkInstance;
import com.testswap.api.service.ForkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forks")
public class ForkController {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;

    private final ForkService forkService;
    private final AppProperties appProps;

    public ForkController(ForkService forkService, AppProperties appProps) {
        this.forkService = forkService;
        this.appProps = appProps;
    }

    @PostMapping
    public ResponseEntity<ForkDtos.Response> create(@RequestBody ForkDtos.CreateRequest req) {
        String rpc = (req.getRpcUrl() != null && !req.getRpcUrl().isEmpty())
                ? req.getRpcUrl() : appProps.getRpc().getUrl();
        ForkInstance inst = forkService.create(req.getLabel(), rpc, req.getBaseBlock());
        return ResponseEntity.ok(toDto(inst));
    }

    @GetMapping
    public ResponseEntity<List<ForkDtos.Response>> list() {
        List<ForkDtos.Response> out = forkService.list().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        boolean ok = forkService.delete(id);
        return ResponseEntity.ok(Map.of("deleted", ok));
    }

    private ForkDtos.Response toDto(ForkInstance i) {
        ForkDtos.Response r = new ForkDtos.Response();
        r.setId(i.getId());
        r.setLabel(i.getLabel());
        r.setRpcUrl(i.getRpcUrl());
        r.setBaseBlock(i.getBaseBlock());
        r.setCreatedAt(ISO.format(i.getCreatedAt()));
        return r;
    }
}
