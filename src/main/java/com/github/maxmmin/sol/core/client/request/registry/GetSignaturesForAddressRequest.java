package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetSignaturesForAddressConfig;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.signature.SignatureInformation;

import java.util.List;
import java.util.Map;

public class GetSignaturesForAddressRequest extends MultiEncRequest<List<SignatureInformation>, Void, Void, Void> {
    private final String address;
    private final GetSignaturesForAddressConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetSignaturesForAddressRequest(RpcGateway gateway, String address, GetSignaturesForAddressConfig config) {
        super(new RpcTypes<List<SignatureInformation>, Void, Void, Void>() {}, gateway);
        this.address = address;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfg = objectMapper.convertValue(config, new TypeReference<Map<String, Object>> () {});
        if (!encoding.isNil()) cfg.put("encoding", encoding);
        return new RpcRequest("getSignaturesForAddress", List.of(address, cfg));
    }

    @Override
    public List<SignatureInformation> send() throws RpcException {
        return super.send();
    }

    @Override
    public Void base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public Void base64() throws RpcException, UnsupportedOperationException {
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
