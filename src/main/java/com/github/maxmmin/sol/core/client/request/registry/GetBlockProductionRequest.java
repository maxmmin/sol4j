package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.GetBlockProductionConfig;
import com.github.maxmmin.sol.core.type.response.block.BlockProduction;

import java.util.List;

public class GetBlockProductionRequest extends SimpleRequest<BlockProduction> {
    public GetBlockProductionRequest(RpcGateway rpcGateway, GetBlockProductionConfig config) {
        super(new TypeReference<BlockProduction>() {}, rpcGateway, "getBlockProduction", List.of(config));
    }

    @Override
    public BlockProduction send() throws RpcException {
        return super.send();
    }
}
