package com.testswap.api.service;

import com.testswap.api.model.ForkInstance;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ForkService {
    private final Map<String, ForkInstance> store = new ConcurrentHashMap<>();
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;

    public ForkInstance create(String label, String rpcUrl, Long baseBlock) {
        ForkInstance inst = new ForkInstance(label, rpcUrl, baseBlock);
        store.put(inst.getId(), inst);
        return inst;
    }

    public List<ForkInstance> list() {
        return new ArrayList<>(store.values());
    }

    public Optional<ForkInstance> get(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }
}
