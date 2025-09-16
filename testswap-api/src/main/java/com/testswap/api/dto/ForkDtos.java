package com.testswap.api.dto;

public class ForkDtos {
    public static class CreateRequest {
        private String label;
        private String rpcUrl;
        private Long baseBlock;

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public String getRpcUrl() { return rpcUrl; }
        public void setRpcUrl(String rpcUrl) { this.rpcUrl = rpcUrl; }
        public Long getBaseBlock() { return baseBlock; }
        public void setBaseBlock(Long baseBlock) { this.baseBlock = baseBlock; }
    }

    public static class Response {
        private String id;
        private String label;
        private String rpcUrl;
        private Long baseBlock;
        private String createdAt;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public String getRpcUrl() { return rpcUrl; }
        public void setRpcUrl(String rpcUrl) { this.rpcUrl = rpcUrl; }
        public Long getBaseBlock() { return baseBlock; }
        public void setBaseBlock(Long baseBlock) { this.baseBlock = baseBlock; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }
}
