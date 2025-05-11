package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetBalanceConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;

import java.math.BigInteger;
import java.util.List;

public class GetBalanceRequest extends SimpleRequest<ContextWrapper<BigInteger>> {
    public GetBalanceRequest(RpcGateway rpcGateway, String pubKey, GetBalanceConfig config) {
        super(new TypeReference<ContextWrapper<BigInteger>>() {}, rpcGateway, "getBalance", List.of(pubKey, config));
    }

    @Override
    public ContextWrapper<BigInteger> send() throws RpcException {
        return super.send();
    }
}
