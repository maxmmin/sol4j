package com.github.maxmmin.sol.core.client.request.enc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.Request;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.RpcResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BatchedMultiEncRequest<D, B, J, P> extends IntrospectedRpcVariety<D, B, J, P> implements Request<List<D>> {
    private final RpcGateway gateway;
    private final List<? extends MultiEncRequest<D, B, J, P>> requests;

    public BatchedMultiEncRequest(RpcGateway gateway, Collection<? extends MultiEncRequest<D, B, J, P>> requests) {
        this.gateway = gateway;
        this.requests = List.copyOf(requests);
    }

    protected <T> List<T> execute(TypeReference<T> typeReference, Encoding encoding) throws RpcException {
        List<RpcRequest> rpcRequests = requests.stream().map(request -> request.constructRpcRequest(encoding)).collect(Collectors.toList());
        return gateway.sendBatched(rpcRequests, typeReference).stream()
                .map(RpcResponse::getResult)
                .collect(Collectors.toList());
    }

    @Override
    public List<D> send() throws RpcException {
        return execute(getTypesMetadata().getDefaultType(), Encoding.NIL);
    }

    public List<B> base58() throws RpcException, UnsupportedOperationException {
        return execute(getTypesMetadata().getBaseEncType(), Encoding.BASE58);
    }

    public List<B> base64() throws RpcException, UnsupportedOperationException {
        return execute(getTypesMetadata().getBaseEncType(), Encoding.BASE64);
    }

    public List<J> json() throws RpcException, UnsupportedOperationException {
        return execute(getTypesMetadata().getJsonType(), Encoding.JSON);
    }

    public List<P> jsonParsed() throws RpcException, UnsupportedOperationException {
        return execute(getTypesMetadata().getJsonParsedType(), Encoding.JSON_PARSED);
    }

    public List<? extends MultiEncRequest<D, B, J, P>> getRequests() {
        return requests;
    }

    public BatchedMultiEncRequest<D, B, J, P> add(MultiEncRequest<D, B, J, P> request) {
        List<MultiEncRequest<D, B, J, P>> newRequests = new ArrayList<>(requests);
        newRequests.add(request);
        return new BatchedMultiEncRequest<>(gateway, newRequests);
    }
}
