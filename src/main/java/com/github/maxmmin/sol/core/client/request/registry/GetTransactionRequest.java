package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetTransactionConfig;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.tx.base.BaseEncTransaction;
import com.github.maxmmin.sol.core.type.response.tx.json.JsonTransaction;
import com.github.maxmmin.sol.core.type.response.tx.jsonparsed.JsonParsedTransaction;

import java.util.List;
import java.util.Map;


public class GetTransactionRequest extends MultiEncRequest<JsonTransaction, BaseEncTransaction, JsonTransaction, JsonParsedTransaction> {
    private final String signature;
    private final GetTransactionConfig config;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetTransactionRequest(RpcGateway gateway, String signature, GetTransactionConfig config) {
        super(new RpcTypes<JsonTransaction, BaseEncTransaction, JsonTransaction, JsonParsedTransaction>() {}, gateway);
        this.signature = signature;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> mapCfg = mapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        if (!encoding.isNil()) mapCfg.put("encoding", encoding);
        return new RpcRequest("getTransaction", List.of(signature, mapCfg));
    }

    @Override
    public JsonTransaction send() throws RpcException {
        return super.send();
    }

    @Override
    public BaseEncTransaction base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public BaseEncTransaction base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public JsonTransaction json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public JsonParsedTransaction jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
