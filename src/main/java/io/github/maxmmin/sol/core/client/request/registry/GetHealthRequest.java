package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;

import java.util.List;

public class GetHealthRequest extends SimpleRequest<String> {
    public GetHealthRequest(RpcGateway rpcGateway) {
        super(new TypeReference<String>() {}, rpcGateway, "getHealth", List.of());
    }

    @Override
    public String send() throws RpcException {
        return super.send();
    }
}
