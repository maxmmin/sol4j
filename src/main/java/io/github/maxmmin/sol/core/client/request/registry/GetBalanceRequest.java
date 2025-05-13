package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetBalanceConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetBalanceRequest extends SimpleRequest<ContextWrapper<BigInteger>> {
    public GetBalanceRequest(RpcGateway rpcGateway, String pubKey, @Nullable GetBalanceConfig config) {
        super(new TypeReference<ContextWrapper<BigInteger>>() {}, rpcGateway, "getBalance", getParams(pubKey, config));
    }

    private static List<Object> getParams(String pubKey, @Nullable GetBalanceConfig config) {
        return Stream.of(Objects.requireNonNull(pubKey, "Pubkey must not be null"), config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ContextWrapper<BigInteger> send() throws RpcException {
        return super.send();
    }
}
