package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetBlocksConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetBlocksWithLimitRequest extends SimpleRequest<List<BigInteger>> {
    public GetBlocksWithLimitRequest(RpcGateway rpcGateway, BigInteger startBlock, @Nullable BigInteger limit, @Nullable GetBlocksConfig config) {
        super(new TypeReference<List<BigInteger>>(){}, rpcGateway, "getBlocksWithLimit", getParams(startBlock, limit, config));
    }

    private static List<Object> getParams(BigInteger startBlock, @Nullable BigInteger limit, @Nullable GetBlocksConfig config) {
        return Stream.of(Objects.requireNonNull(startBlock, "Start block must be specified"), limit, config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BigInteger> send() throws RpcException {
        return super.send();
    }
}
