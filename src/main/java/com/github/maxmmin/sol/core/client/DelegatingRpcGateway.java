package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.RpcResponse;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DelegatingRpcGateway implements RpcGateway {
    private final RpcGateway rpcGateway;

    @Override
    public <V> void sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference, Map<String, RpcResponse<V>> target) throws RpcException {
        beforeEach(new RequestContext(target.size()));
        rpcGateway.sendBatched(requests, typeReference, target);
    }

    @Override
    public <V> RpcResponse<V> send(RpcRequest request, TypeReference<V> typeReference) throws RpcException {
        beforeEach(new RequestContext());
        return rpcGateway.send(request, typeReference);
    }

    @Override
    public <V> List<RpcResponse<V>> sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference) throws RpcException {
        beforeEach(new RequestContext(requests.size()));
        return rpcGateway.sendBatched(requests, typeReference);
    }


    @Override
    public String getEndpoint() {
        return rpcGateway.getEndpoint();
    }

    protected void beforeEach(RequestContext requestContext) {}

    @Getter
    @RequiredArgsConstructor
    protected static class RequestContext {
        private final int weight;

        public RequestContext() {
            this.weight = 1;
        }
    }
}
