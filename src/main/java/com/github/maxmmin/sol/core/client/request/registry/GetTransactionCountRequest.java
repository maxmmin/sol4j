package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.GetTransactionCountConfig;

import java.math.BigInteger;
import java.util.List;

public class GetTransactionCountRequest extends SimpleRequest<BigInteger> {
    public GetTransactionCountRequest(RpcGateway gateway, GetTransactionCountConfig config) {
        super(new TypeReference<BigInteger>() {}, gateway, "getTransactionCount", List.of(config));
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
