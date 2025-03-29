package com.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.util.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SimpleRequest<V> implements Request<V> {
    private final RpcGateway rpcGateway;
    private final String method;
    private final List<Object> params;
    private final TypeReference<V> targetReference;

    public SimpleRequest(RpcGateway rpcGateway, String method, List<Object> params) {
        this.rpcGateway = rpcGateway;
        this.method = method;
        this.params = params;
        this.targetReference = introspectTypeRef();
    }

    protected TypeReference<V> introspectTypeRef() {
        Type relative = this.getClass().getGenericInterfaces()[0];
        if (!(relative instanceof ParameterizedType)) throw new RuntimeException("Expected parameterized type");
        else return Types.asRef(((ParameterizedType) relative).getActualTypeArguments()[0]);
    }

    @Override
    public V send() throws RpcException {
        RpcRequest rpcRequest = new RpcRequest(method, params);
        return rpcGateway.send(rpcRequest, targetReference).getResult();
    }
}
