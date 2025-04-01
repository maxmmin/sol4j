package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;

import java.util.List;

public class GetGenesisHashRequest extends SimpleRequest<String> {
    public GetGenesisHashRequest(RpcGateway rpcGateway) {
        super(new TypeReference<String>() {}, rpcGateway, "getGenesisHash", List.of());
    }
}
