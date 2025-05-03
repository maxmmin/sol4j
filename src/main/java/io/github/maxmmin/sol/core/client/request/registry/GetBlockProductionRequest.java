package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.type.request.GetBlockProductionConfig;
import io.github.maxmmin.sol.core.client.type.response.block.BlockProduction;

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
