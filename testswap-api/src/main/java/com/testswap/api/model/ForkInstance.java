package com.testswap.api.model;

import java.time.Instant;
import java.util.UUID;

public class ForkInstance {
    private final String id;
    private String label;
    private String rpcUrl;
    private Long baseBlock;
    private Instant createdAt;

    public ForkInstance(String label, String rpcUrl, Long baseBlock) {
        this.id = UUID.randomUUID().toString();
        this.label = label;
        this.rpcUrl = rpcUrl;
        this.baseBlock = baseBlock;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getRpcUrl() { return rpcUrl; }
    public void setRpcUrl(String rpcUrl) { this.rpcUrl = rpcUrl; }
    public Long getBaseBlock() { return baseBlock; }
    public void setBaseBlock(Long baseBlock) { this.baseBlock = baseBlock; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
