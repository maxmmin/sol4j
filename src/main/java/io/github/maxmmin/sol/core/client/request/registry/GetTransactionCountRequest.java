package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetTransactionCountConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetTransactionCountRequest extends SimpleRequest<BigInteger> {
    public GetTransactionCountRequest(RpcGateway gateway, @Nullable GetTransactionCountConfig config) {
        super(new TypeReference<BigInteger>() {}, gateway, "getTransactionCount", getParams(config));
    }

    private static List<Object> getParams(@Nullable GetTransactionCountConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
