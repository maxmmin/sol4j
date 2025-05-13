package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetBlockHeightConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetBlockHeightRequest extends SimpleRequest<BigInteger> {
    public GetBlockHeightRequest(RpcGateway rpcGateway, @Nullable GetBlockHeightConfig config) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getBlockHeight", getParams(config));
    }

    private static List<Object> getParams(@Nullable GetBlockHeightConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
