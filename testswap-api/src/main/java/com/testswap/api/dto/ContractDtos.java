package com.testswap.api.dto;

import java.util.Map;

public class ContractDtos {
    public static class CallRequest {
        private String address; // 合约地址
        private String method;  // 方法名
        private Map<String, Object> params; // 参数键值
        private String rpcUrl; // 可选，覆盖默认 RPC

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
        public Map<String, Object> getParams() { return params; }
        public void setParams(Map<String, Object> params) { this.params = params; }
        public String getRpcUrl() { return rpcUrl; }
        public void setRpcUrl(String rpcUrl) { this.rpcUrl = rpcUrl; }
    }

    public static class SendRequest extends CallRequest {
        private String from;      // 交易发起地址
        private String privateKey; // 可选：本地签名（演示用，不建议线上）
        private String value;     // 可选：转账金额（wei）

        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getPrivateKey() { return privateKey; }
        public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }

    public static class DeployRequest {
        private String template; // e.g., ERC20 / Pool / Vesting
        private Map<String, Object> args; // 构造参数
        private String from;
        private String privateKey; // 可选
        private String rpcUrl;     // 可选

        public String getTemplate() { return template; }
        public void setTemplate(String template) { this.template = template; }
        public Map<String, Object> getArgs() { return args; }
        public void setArgs(Map<String, Object> args) { this.args = args; }
        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getPrivateKey() { return privateKey; }
        public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }
        public String getRpcUrl() { return rpcUrl; }
        public void setRpcUrl(String rpcUrl) { this.rpcUrl = rpcUrl; }
    }

    public static class GenericResponse {
        private boolean success;
        private String message;
        private Map<String, Object> data;

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Map<String, Object> getData() { return data; }
        public void setData(Map<String, Object> data) { this.data = data; }
    }
}
