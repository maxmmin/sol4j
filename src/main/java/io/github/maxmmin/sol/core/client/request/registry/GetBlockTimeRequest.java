package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;

import java.math.BigInteger;
import java.util.List;

public class GetBlockTimeRequest extends SimpleRequest<Long> {
    public GetBlockTimeRequest(RpcGateway rpcGateway, BigInteger blockNumber) {
        super(new TypeReference<Long>() {}, rpcGateway, "getBlockTime", List.of(blockNumber));
    }

    @Override
    public Long send() throws RpcException {
        return super.send();
    }
}
