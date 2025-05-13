package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.request.SendTransactionConfig;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendTransactionRequest extends MultiEncRequest<String, String, Void, Void> {
    private final String encodedTransaction;
    private final SendTransactionConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SendTransactionRequest(RpcGateway gateway, String encodedTransaction, @Nullable SendTransactionConfig config) {
        super(
                new RpcTypes<String, String, Void, Void>() {},
                new EncodingSupport(Encoding.BASE58, Encoding.BASE64),
                gateway
        );
        this.encodedTransaction = encodedTransaction;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = config == null ? new HashMap<>() : objectMapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        cfgMap.put("encoding", encoding);
        return new RpcRequest("sendTransaction", List.of(encodedTransaction, cfgMap));
    }

    @Override
    public String send() throws RpcException {
        return super.send();
    }

    @Override
    public String base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public String base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public Void json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public Void jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
