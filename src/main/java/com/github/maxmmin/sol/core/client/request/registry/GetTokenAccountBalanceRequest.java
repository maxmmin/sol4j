package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.GetTokenAccountBalanceConfig;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.token.TokenAccountBalance;

import java.util.List;

public class GetTokenAccountBalanceRequest extends SimpleRequest<ContextWrapper<TokenAccountBalance>> {
    public GetTokenAccountBalanceRequest(RpcGateway rpcGateway, String publicKey, GetTokenAccountBalanceConfig config) {
        super(new TypeReference<ContextWrapper<TokenAccountBalance>>() {}, rpcGateway, "getTokenAccountBalance", List.of(publicKey, config));
    }

    @Override
    public ContextWrapper<TokenAccountBalance> send() throws RpcException {
        return super.send();
    }
}
