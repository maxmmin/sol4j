package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.type.response.node.Version;

import java.util.List;

public class GetVersionRequest extends SimpleRequest<Version> {
    public GetVersionRequest(RpcGateway rpcGateway) {
        super(new TypeReference<Version>() {}, rpcGateway, "getVersion", List.of());
    }

    @Override
    public Version send() throws RpcException {
        return super.send();
    }
}
