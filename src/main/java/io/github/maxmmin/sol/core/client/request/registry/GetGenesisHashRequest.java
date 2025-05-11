package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;

import java.util.List;

public class GetGenesisHashRequest extends SimpleRequest<String> {
    public GetGenesisHashRequest(RpcGateway rpcGateway) {
        super(new TypeReference<String>() {}, rpcGateway, "getGenesisHash", List.of());
    }

    @Override
    public String send() throws RpcException {
        return super.send();
    }
}
