package com.testswap.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class MetaController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "testswap-api");
        body.put("status", "ok");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/healthz")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "ok");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/version")
    public ResponseEntity<Map<String, Object>> version() {
        Map<String, Object> body = new HashMap<>();
        body.put("version", "0.1.0");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/api/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "pong");
        return ResponseEntity.ok(body);
    }
}
