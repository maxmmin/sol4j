package io.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.request.RpcRequest;

import java.util.List;

public class SimpleRequest<V> implements Request<V> {
    private final RpcGateway rpcGateway;
    private final String method;
    private final List<Object> params;
    private final TypeReference<V> targetReference;

    public SimpleRequest(TypeReference<V>targetReference, RpcGateway rpcGateway, String method, List<Object> params) {
        this.targetReference = targetReference;
        this.rpcGateway = rpcGateway;
        this.method = method;
        this.params = params;
    }

    @Override
    public RpcRequest construct() {
        return new RpcRequest(method, params);
    }

    @Override
    public V send() throws RpcException {
        RpcRequest rpcRequest = construct();
        return rpcGateway.send(rpcRequest, targetReference).getResult();
    }
}
