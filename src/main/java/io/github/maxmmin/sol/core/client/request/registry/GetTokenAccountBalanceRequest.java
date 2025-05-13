package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetTokenAccountBalanceConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.token.TokenAccountBalance;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetTokenAccountBalanceRequest extends SimpleRequest<ContextWrapper<TokenAccountBalance>> {
    public GetTokenAccountBalanceRequest(RpcGateway rpcGateway, String publicKey, @Nullable GetTokenAccountBalanceConfig config) {
        super(new TypeReference<ContextWrapper<TokenAccountBalance>>() {}, rpcGateway, "getTokenAccountBalance", getParams(publicKey, config));
    }

    private static List<Object> getParams(String publicKey, @Nullable GetTokenAccountBalanceConfig config) {
        return Stream.of(
                Objects.requireNonNull(publicKey, "Public key must be specified"),
                config
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ContextWrapper<TokenAccountBalance> send() throws RpcException {
        return super.send();
    }
}
