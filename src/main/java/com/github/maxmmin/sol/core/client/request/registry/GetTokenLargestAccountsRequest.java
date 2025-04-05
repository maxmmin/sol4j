package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.type.request.GetTokenLargestAccountsConfig;
import com.github.maxmmin.sol.core.type.response.token.TokenAccountInfo;

import java.util.List;

public class GetTokenLargestAccountsRequest extends SimpleRequest<TokenAccountInfo> {
    public GetTokenLargestAccountsRequest (RpcGateway rpcGateway, String publicKey, GetTokenLargestAccountsConfig config) {
        super(new TypeReference<TokenAccountInfo>() {}, rpcGateway, "getTokenLargestAccounts", List.of(publicKey, config));
    }
}
