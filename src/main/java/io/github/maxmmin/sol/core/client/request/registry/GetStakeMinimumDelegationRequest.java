package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetStakeMinimumDelegationConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;

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
