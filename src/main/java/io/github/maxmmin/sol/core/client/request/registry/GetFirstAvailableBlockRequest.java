package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;

import java.math.BigInteger;
import java.util.List;

public class GetFirstAvailableBlockRequest extends SimpleRequest<BigInteger> {
    public GetFirstAvailableBlockRequest(RpcGateway rpcGateway) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getFirstAvailableBlock", List.of());
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
