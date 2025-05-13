package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class GetBlockTimeRequest extends SimpleRequest<Long> {
    public GetBlockTimeRequest(RpcGateway rpcGateway, BigInteger blockNumber) {
        super(new TypeReference<Long>() {}, rpcGateway, "getBlockTime", getParams(blockNumber));
    }

    private static List<Object> getParams(BigInteger blockNumber) {
        return List.of(Objects.requireNonNull(blockNumber, "Block number must be specified"));
    }

    @Override
    public Long send() throws RpcException {
        return super.send();
    }
}
