package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.response.node.Version;

import java.util.List;

public class GetVersionRequest extends SimpleRequest<Version> {
    public GetVersionRequest(RpcGateway rpcGateway) {
        super(new TypeReference<Version>() {}, rpcGateway, "getVersion", List.of());
    }

    @Override
    public Version send() throws RpcException {
        return super.send();
    }
}
