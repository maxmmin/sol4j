package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.GetStakeMinimumDelegationConfig;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;

import java.math.BigInteger;
import java.util.List;

public class GetStakeMinimumDelegationRequest extends SimpleRequest<ContextWrapper<BigInteger>> {
    public GetStakeMinimumDelegationRequest(RpcGateway rpcGateway, GetStakeMinimumDelegationConfig config) {
        super(new TypeReference<ContextWrapper<BigInteger>>() {}, rpcGateway, "getStakeMinimumDelegation", List.of(config));
    }

    @Override
    public ContextWrapper<BigInteger> send() throws RpcException {
        return super.send();
    }
}
