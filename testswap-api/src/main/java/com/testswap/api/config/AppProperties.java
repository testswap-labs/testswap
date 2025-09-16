package com.testswap.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "testswap")
public class AppProperties {
    private final Rpc rpc = new Rpc();
    private final Fork fork = new Fork();

    public Rpc getRpc() { return rpc; }
    public Fork getFork() { return fork; }

    public static class Rpc {
        /** Ethereum RPC endpoint URL */
        private String url;
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public static class Fork {
        /** Optional base block number for fork */
        private Long block;
        public Long getBlock() { return block; }
        public void setBlock(Long block) { this.block = block; }
    }
}
