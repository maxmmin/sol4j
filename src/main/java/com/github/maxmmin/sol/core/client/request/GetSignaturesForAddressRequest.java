package com.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetSignaturesForAddressConfig;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.signature.SignatureInformation;

import java.util.List;
import java.util.Map;

public class GetSignaturesForAddressRequest extends ExecRequest<List<SignatureInformation>, Void, Void, Void> {
    private final String address;
    private final GetSignaturesForAddressConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetSignaturesForAddressRequest(RpcGateway gateway, String address, GetSignaturesForAddressConfig config) {
        super(gateway);
        this.address = address;
        this.config = config;
    }

    @Override
    protected RpcRequest constructRpcRequest(Encoding encoding) {
        Map<String, Object> cfg = objectMapper.convertValue(config, new TypeReference<Map<String, Object>> () {});
        if (!encoding.isNil()) cfg.put("encoding", encoding);
        return new RpcRequest("getSignaturesForAddress", List.of(address, cfg));
    }
}
