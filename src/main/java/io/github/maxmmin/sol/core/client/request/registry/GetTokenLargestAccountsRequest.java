package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetTokenLargestAccountsConfig;
import io.github.maxmmin.sol.core.client.type.response.token.TokenAccountInfo;

import java.util.List;

public class GetTokenLargestAccountsRequest extends SimpleRequest<TokenAccountInfo> {
    public GetTokenLargestAccountsRequest (RpcGateway rpcGateway, String publicKey, GetTokenLargestAccountsConfig config) {
        super(new TypeReference<TokenAccountInfo>() {}, rpcGateway, "getTokenLargestAccounts", List.of(publicKey, config));
    }
}
