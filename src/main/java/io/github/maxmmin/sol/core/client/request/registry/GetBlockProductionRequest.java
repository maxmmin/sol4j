package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetBlockProductionConfig;
import io.github.maxmmin.sol.core.client.type.response.block.BlockProduction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetBlockProductionRequest extends SimpleRequest<BlockProduction> {
    public GetBlockProductionRequest(RpcGateway rpcGateway, @Nullable GetBlockProductionConfig config) {
        super(new TypeReference<BlockProduction>() {}, rpcGateway, "getBlockProduction", getParams(config));
    }

    private static List<Object> getParams (@Nullable GetBlockProductionConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public BlockProduction send() throws RpcException {
        return super.send();
    }
}
