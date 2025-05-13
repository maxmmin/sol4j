package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;

import java.math.BigInteger;
import java.util.List;

public class GetMaxShredInsertSlotRequest extends SimpleRequest<BigInteger> {
    public GetMaxShredInsertSlotRequest(RpcGateway rpcGateway) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getMaxShredInsertSlot", List.of());
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
