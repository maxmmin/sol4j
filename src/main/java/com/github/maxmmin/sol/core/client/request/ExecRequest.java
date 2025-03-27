package com.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.RpcRequest;

public abstract class ExecRequest<D, B, J, P> extends TypedRequest<D, B, J, P> {
    private final RpcGateway gateway;

    public ExecRequest(RpcGateway gateway) {
        this.gateway = gateway;
    }

    protected abstract RpcRequest constructRpcRequest(Encoding encoding);

    @Override
    public D noarg() throws RpcException {
        return send(getTypesMetadata().getDefaultType(), Encoding.NIL);
    }

    @Override
    public B base58() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getBaseEncType(), Encoding.BASE58);
    }

    @Override
    public B base64() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getBaseEncType(), Encoding.BASE64);
    }

    @Override
    public J json() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getJsonType(), Encoding.JSON);
    }

    @Override
    public P jsonParsed() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getJsonParsedType(), Encoding.JSON_PARSED);
    }

    protected <T> T send(TypeReference<T> typeReference, Encoding encoding) throws RpcException {
        if (encoding != Encoding.NIL && !getSupportedEncodings().contains(encoding)) {
            throw new UnsupportedOperationException("Unsupported encoding: " + encoding);
        }
        return gateway.send(constructRpcRequest(encoding), typeReference).getResult();
    }
}
