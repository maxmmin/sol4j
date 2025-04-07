package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.GetBlockHeightConfig;

import java.math.BigInteger;
import java.util.List;

public class GetBlockHeightRequest extends SimpleRequest<BigInteger> {
    public GetBlockHeightRequest(RpcGateway rpcGateway, GetBlockHeightConfig config) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getBlockHeight", List.of(config));
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
