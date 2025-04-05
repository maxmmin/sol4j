package io.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.RpcGateway;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.response.RpcResponse;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleBatchedRequest<V> implements BatchedRequest<V> {
    private final RpcGateway rpcGateway;
    private final List<Request<V>> requests;
    private final TypeReference<V> typeReference;

    public SimpleBatchedRequest(TypeReference<V> targetType, RpcGateway rpcGateway, List<Request<V>> requests) {
        this.typeReference = targetType;
        this.rpcGateway = rpcGateway;
        this.requests = requests;
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

}
