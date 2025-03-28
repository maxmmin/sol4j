package com.github.maxmmin.sol.core.client.request.enc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.RpcRequest;

public abstract class MultiEncRequest<D, B, J, P> extends IntrospectedRpcVariety<D, B, J, P> {
    private final RpcGateway gateway;

    public MultiEncRequest(RpcGateway gateway) {
        this.gateway = gateway;
    }

    protected abstract RpcRequest constructRpcRequest(Encoding encoding);

    public D noarg() throws RpcException {
        return send(getTypesMetadata().getDefaultType(), Encoding.NIL);
    }

    public B base58() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getBaseEncType(), Encoding.BASE58);
    }

    public B base64() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getBaseEncType(), Encoding.BASE64);
    }

    public J json() throws RpcException, UnsupportedOperationException {
        return send(getTypesMetadata().getJsonType(), Encoding.JSON);
    }

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
