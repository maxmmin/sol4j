package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;

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
