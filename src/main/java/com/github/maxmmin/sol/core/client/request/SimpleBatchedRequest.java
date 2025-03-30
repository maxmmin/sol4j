package com.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.RpcResponse;
import com.github.maxmmin.sol.util.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleBatchedRequest<V> implements BatchedRequest<V> {
    private final RpcGateway rpcGateway;
    private final List<Request<V>> requests;
    private final TypeReference<V> typeReference;

    public SimpleBatchedRequest(RpcGateway rpcGateway, List<Request<V>> requests) {
        this.rpcGateway = rpcGateway;
        this.requests = requests;
        this.typeReference = introspectTypeRef();
    }

    @Override
    public List<V> send() throws RpcException {
        List<RpcRequest>rpcRequests = requests.stream()
                .map(Request::construct)
                .collect(Collectors.toList());

        return rpcGateway.sendBatched(rpcRequests, typeReference)
                .stream()
                .map(RpcResponse::getResult)
                .collect(Collectors.toList());
    }

    protected TypeReference<V> introspectTypeRef() {
        Type relative = this.getClass().getGenericInterfaces()[0];
        if (!(relative instanceof ParameterizedType)) throw new RuntimeException("Expected parameterized type");
        else return Types.asRef(((ParameterizedType) relative).getActualTypeArguments()[0]);
    }
}
