package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.GetLatestBlockhashConfig;
import com.github.maxmmin.sol.core.type.response.block.LatestBlockhashInfo;

import java.util.List;

public class GetLatestBlockhashRequest extends SimpleRequest<LatestBlockhashInfo> {
    public GetLatestBlockhashRequest(RpcGateway rpcGateway, GetLatestBlockhashConfig config) {
        super(new TypeReference<LatestBlockhashInfo>() {}, rpcGateway, "getLatestBlockhash", List.of(config));
    }

    @Override
    public LatestBlockhashInfo send() throws RpcException {
        return super.send();
    }
}
