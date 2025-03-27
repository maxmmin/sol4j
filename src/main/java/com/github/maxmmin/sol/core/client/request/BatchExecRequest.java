package com.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.RpcResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BatchExecRequest<D, B, J, P> extends TypedRequest<D, B, J, P> {
    private final RpcGateway gateway;
    private final List<? extends ExecRequest<D, B, J, P>> requests;

    public BatchExecRequest(RpcGateway gateway, Collection<? extends ExecRequest<D, B, J, P>> requests) {
        this.gateway = gateway;
        this.requests = List.copyOf(requests);
    }

    protected <T> List<T> send (TypeReference<T> typeReference, Encoding encoding) throws RpcException {
        List<RpcRequest> rpcRequests = requests.stream().map(request -> request.constructRpcRequest(encoding)).collect(Collectors.toList());
        return gateway.sendBatched(rpcRequests, typeReference).stream()
                .map(RpcResponse::getResult)
                .collect(Collectors.toList());
    }


    @Override
    public D noarg() throws RpcException {
        return send()
    }

    @Override
    public B base58() throws RpcException, UnsupportedOperationException {
        return null;
    }

    @Override
    public B base64() throws RpcException, UnsupportedOperationException {
        return null;
    }

    @Override
    public J json() throws RpcException, UnsupportedOperationException {
        return null;
    }

    @Override
    public P jsonParsed() throws RpcException, UnsupportedOperationException {
        return null;
    }
}
