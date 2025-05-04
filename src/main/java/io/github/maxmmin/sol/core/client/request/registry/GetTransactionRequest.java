package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.request.GetTransactionConfig;
import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.response.tx.base.BaseEncConfirmedTransaction;
import io.github.maxmmin.sol.core.client.type.response.tx.json.JsonConfirmedTransaction;
import io.github.maxmmin.sol.core.client.type.response.tx.jsonparsed.JsonParsedConfirmedTransaction;

import java.util.List;
import java.util.Map;


public class GetTransactionRequest extends MultiEncRequest<JsonConfirmedTransaction, BaseEncConfirmedTransaction, JsonConfirmedTransaction, JsonParsedConfirmedTransaction> {
    private final String signature;
    private final GetTransactionConfig config;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetTransactionRequest(RpcGateway gateway, String signature, GetTransactionConfig config) {
        super(
                new RpcTypes<JsonConfirmedTransaction, BaseEncConfirmedTransaction, JsonConfirmedTransaction, JsonParsedConfirmedTransaction>() {},
                EncodingSupport.fullWithCompressing(),
                gateway
        );
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
    public JsonConfirmedTransaction send() throws RpcException {
        return super.send();
    }

    @Override
    public BaseEncConfirmedTransaction base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public BaseEncConfirmedTransaction base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public JsonConfirmedTransaction json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public JsonParsedConfirmedTransaction jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
