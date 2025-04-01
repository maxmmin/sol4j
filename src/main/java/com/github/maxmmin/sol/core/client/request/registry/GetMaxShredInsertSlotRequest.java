package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;

import java.math.BigInteger;
import java.util.List;

public class GetMaxShredInsertSlotRequest extends SimpleRequest<BigInteger> {
    public GetMaxShredInsertSlotRequest(RpcGateway rpcGateway) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getMaxShredInsert", List.of());
    }

    @Override
    public BigInteger send() throws RpcException {
        return super.send();
    }
}
