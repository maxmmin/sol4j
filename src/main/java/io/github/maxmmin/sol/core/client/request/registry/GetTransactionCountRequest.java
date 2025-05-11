package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetTransactionCountConfig;

import java.math.BigInteger;
import java.util.List;

public class GetTransactionCountRequest extends SimpleRequest<BigInteger> {
    public GetTransactionCountRequest(RpcGateway gateway, GetTransactionCountConfig config) {
        super(new TypeReference<BigInteger>() {}, gateway, "getTransactionCount", List.of(config));
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
