package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;

import java.util.List;

public class GetIdentityRequest extends SimpleRequest<String> {
    public GetIdentityRequest(RpcGateway rpcGateway) {
        super(new TypeReference<String>() {}, rpcGateway, "getIdentity", List.of());
    }
}
