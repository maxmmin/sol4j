package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;

import java.math.BigInteger;
import java.util.List;

public class GetBlockCommitmentRequest extends SimpleRequest<BigInteger> {
    public GetBlockCommitmentRequest(RpcGateway rpcGateway, BigInteger blockNumber) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getBlockCommitment", List.of(blockNumber));
    }
}
