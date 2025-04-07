package io.github.maxmmin.sol.core.client.request.enc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.Request;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.core.type.request.RpcRequest;


/**
 *
 * @param <D> - Default response type
 * @param <B> - Base encoded response type
 * @param <J> - JSON response type
 * @param <P> - JSON Parsed response type
 *
**/
public abstract class MultiEncRequest<D, B, J, P> extends IntrospectedRpcVariety<D, B, J, P> implements Request<D> {
    private final RpcGateway gateway;

    public MultiEncRequest(RpcTypes<D, B, J, P> types, RpcGateway gateway) {
        super(types);
        this.gateway = gateway;
    }

    protected abstract RpcRequest construct(Encoding encoding);

    @Override
    public RpcRequest construct() {
        return construct(Encoding.NIL);
    }

    @Override
    public D send() throws RpcException {
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
        return gateway.send(construct(encoding), typeReference).getResult();
    }
}
