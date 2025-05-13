package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetStakeMinimumDelegationConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetStakeMinimumDelegationRequest extends SimpleRequest<ContextWrapper<BigInteger>> {
    public GetStakeMinimumDelegationRequest(RpcGateway rpcGateway, @Nullable GetStakeMinimumDelegationConfig config) {
        super(new TypeReference<ContextWrapper<BigInteger>>() {}, rpcGateway, "getStakeMinimumDelegation", getParams(config));
    }

    private static List<Object> getParams(@Nullable GetStakeMinimumDelegationConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ContextWrapper<BigInteger> send() throws RpcException {
        return super.send();
    }
}
