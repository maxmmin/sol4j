package io.github.maxmmin.sol.core.client.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.type.response.block.BaseEncBlock;
import io.github.maxmmin.sol.core.client.type.response.block.JsonBlock;
import io.github.maxmmin.sol.core.client.type.response.block.JsonParsedBlock;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class GetBlockRequest extends MultiEncRequest<JsonBlock, BaseEncBlock, JsonBlock, JsonParsedBlock> {
    private final GetBlockConfig config;
    private final BigInteger slotNumber;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetBlockRequest(RpcGateway gateway, BigInteger slotNumber, GetBlockConfig config) {
        super(
                new RpcTypes<JsonBlock, BaseEncBlock, JsonBlock, JsonParsedBlock>() {},
                EncodingSupport.full(),
                gateway
        );
        this.slotNumber = slotNumber;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = mapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        if (!encoding.isNil()) cfgMap.put("encoding", encoding);
        return new RpcRequest("getBlock", List.of(slotNumber, cfgMap));
    }

    @Override
    public JsonBlock send() throws RpcException {
        return super.send();
    }

    @Override
    public BaseEncBlock base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public BaseEncBlock base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public JsonBlock json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public JsonParsedBlock jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
