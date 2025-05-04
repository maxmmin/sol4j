package io.github.maxmmin.sol.core.client.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.response.RpcResponse;

import java.util.List;
import java.util.Map;

public interface RpcGateway {
    <V> RpcResponse<V> send(RpcRequest request, TypeReference<V> typeReference) throws RpcException;
    <V> void sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference, Map<String, RpcResponse<V>> target) throws RpcException;
    <V> List<RpcResponse<V>> sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference) throws RpcException;

    String getEndpoint();
}
