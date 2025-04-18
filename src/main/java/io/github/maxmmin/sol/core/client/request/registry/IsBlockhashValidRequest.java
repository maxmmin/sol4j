package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.IsBlockhashValidConfig;

import java.util.List;

public class IsBlockhashValidRequest extends SimpleRequest<Boolean> {
    public IsBlockhashValidRequest(RpcGateway rpcGateway, String blockHash, IsBlockhashValidConfig config) {
        super(new TypeReference<Boolean>() {}, rpcGateway, "isBlockhashValid", List.of(blockHash, config));
    }

    @Override
    public Boolean send() throws RpcException {
        return super.send();
    }
}
