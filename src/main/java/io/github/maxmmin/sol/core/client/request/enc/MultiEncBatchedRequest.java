package io.github.maxmmin.sol.core.client.request.enc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.BatchedRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.response.RpcResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MultiEncBatchedRequest<D, B, J, P> extends IntrospectedRpcVariety<D, B, J, P> implements BatchedRequest<D> {
    private final RpcGateway gateway;
    private final List<? extends MultiEncRequest<D, B, J, P>> requests;

    public MultiEncBatchedRequest(RpcTypes<D, B, J, P> types, RpcGateway gateway, Collection<? extends MultiEncRequest<D, B, J, P>> requests) {
        super(types);
        this.gateway = gateway;
        this.requests = List.copyOf(requests);
    }

    protected <T> List<T> execute(TypeReference<T> typeReference, Encoding encoding) throws RpcException {
        List<RpcRequest> rpcRequests = requests.stream().map(request -> request.construct(encoding)).collect(Collectors.toList());
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

    public MultiEncBatchedRequest<D, B, J, P> add(MultiEncRequest<D, B, J, P> request) {
        List<MultiEncRequest<D, B, J, P>> newRequests = new ArrayList<>(requests);
        newRequests.add(request);
        return new MultiEncBatchedRequest<>(getRpcTypes(), gateway, newRequests);
    }
}
