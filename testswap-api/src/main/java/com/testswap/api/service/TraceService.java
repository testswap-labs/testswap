package com.testswap.api.service;

import com.testswap.api.dto.TraceDtos;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TraceService {
    public TraceDtos.Response traceTx(TraceDtos.TxRequest req) {
        // 占位：实际实现应调用 debug_traceTransaction 返回调用栈、存储变化等
        Map<String, Object> data = new HashMap<>();
        data.put("txHash", req.getTxHash());
        data.put("rpcUrl", req.getRpcUrl());
        data.put("callStack", new String[]{"CALL contractA.methodX", "CALL contractB.methodY"});
        data.put("storageDiff", new String[]{"slot0: 0x00 -> 0x01"});

        TraceDtos.Response resp = new TraceDtos.Response();
        resp.setSuccess(true);
        resp.setMessage("trace simulated");
        resp.setData(data);
        return resp;
    }
}
