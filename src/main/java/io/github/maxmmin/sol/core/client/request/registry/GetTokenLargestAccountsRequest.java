package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetTokenLargestAccountsConfig;
import io.github.maxmmin.sol.core.client.type.response.token.TokenAccountInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetTokenLargestAccountsRequest extends SimpleRequest<TokenAccountInfo> {
    public GetTokenLargestAccountsRequest (RpcGateway rpcGateway, String publicKey, GetTokenLargestAccountsConfig config) {
        super(new TypeReference<TokenAccountInfo>() {}, rpcGateway, "getTokenLargestAccounts", getParams(publicKey, config));
    }

    private static List<Object> getParams(String publicKey, @Nullable GetTokenLargestAccountsConfig config) {
        return Stream.of(
                Objects.requireNonNull(publicKey, "Public key must be specified"),
                config
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public TokenAccountInfo send() throws RpcException {
        return super.send();
    }
}
