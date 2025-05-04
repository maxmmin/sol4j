package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;

import java.util.List;

public class GetIdentityRequest extends SimpleRequest<String> {
    public GetIdentityRequest(RpcGateway rpcGateway) {
        super(new TypeReference<String>() {}, rpcGateway, "getIdentity", List.of());
    }

    @Override
    public String send() throws RpcException {
        return super.send();
    }
}
