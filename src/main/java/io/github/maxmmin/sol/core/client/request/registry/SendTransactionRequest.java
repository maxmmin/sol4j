package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.request.SendTransactionConfig;

import java.util.List;
import java.util.Map;

public class SendTransactionRequest extends MultiEncRequest<String, String, Void, Void> {
    private final String encodedTransaction;
    private final SendTransactionConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SendTransactionRequest(RpcGateway gateway, String encodedTransaction, SendTransactionConfig config) {
        super(new RpcTypes<String, String, Void, Void>() {}, gateway);
        this.encodedTransaction = encodedTransaction;
        this.config = config;
    }


    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = objectMapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        cfgMap.put("encoding", encoding);
        return new RpcRequest("sendTransaction", List.of(encodedTransaction, cfgMap));
    }

}
