package org.mxmn.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import org.mxmn.sol.core.exception.RpcException;
import org.mxmn.sol.core.type.request.RpcRequest;
import org.mxmn.sol.core.type.response.RpcResponse;

import java.util.List;
import java.util.Map;

public interface RpcGateway {
    <V> RpcResponse<V> send(RpcRequest request, TypeReference<V> typeReference) throws RpcException;
    <V> void sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference, Map<String, RpcResponse<V>> target) throws RpcException;
    <V> List<RpcResponse<V>> sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference) throws RpcException;

    String getEndpoint();
}
