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

public class GetBlocksRequest extends SimpleRequest<List<BigInteger>> {
    public GetBlocksRequest(RpcGateway rpcGateway, BigInteger startBlock, @Nullable BigInteger endBlock, @Nullable GetBlocksConfig config) {
        super(new TypeReference<List<BigInteger>>(){}, rpcGateway, "getBlocks", getParams(startBlock, endBlock, config));
    }

    private static List<Object> getParams(BigInteger startBlock, @Nullable BigInteger endBlock, @Nullable GetBlocksConfig config) {
        return Stream.of(Objects.requireNonNull(startBlock, "Start block must be specified"), endBlock, config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BigInteger> send() throws RpcException {
        return super.send();
    }
}
