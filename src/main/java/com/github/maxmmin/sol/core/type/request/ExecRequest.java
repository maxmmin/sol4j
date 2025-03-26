package com.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 *
 * @param <D> - Default Response Type
 * @param <B> - Base Enc Response Type
 * @param <J> - JSON Response Type
 * @param <P> - JSON Parsed Response Type
 */
public abstract class ExecRequest<D, B, J, P> {
    private final RpcGateway gateway;

    private final TypeReference<D> defaultType;
    private final @Nullable TypeReference<B> baseEncType;
    private final @Nullable TypeReference<J> jsonType;
    private final @Nullable TypeReference<P> jsonParsedType;
    private final Set<Encoding> supportedEncodings;

    protected ExecRequest(RpcGateway gateway, TypeReference<D> defaultType) {
        this(gateway, defaultType, null, null, null);
    }

    public ExecRequest(RpcGateway gateway, TypeReference<D> defaultType, @Nullable TypeReference<B> baseEncType,
                       @Nullable TypeReference<J> jsonType, @Nullable TypeReference<P> jsonParsedType) {
        this.gateway = gateway;
        this.defaultType = defaultType;
        this.baseEncType = baseEncType;
        this.jsonType = jsonType;
        this.jsonParsedType = jsonParsedType;
        this.supportedEncodings = introspectSupportedEncodings();
    }

    protected Set<Encoding> introspectSupportedEncodings() {
        Set<Encoding> supportedEncodings = new HashSet<>();
        supportedEncodings.add(Encoding.NIL);
        if (baseEncType != null) supportedEncodings.addAll(List.of(Encoding.BASE58, Encoding.BASE64));
        if (jsonType != null) supportedEncodings.add(Encoding.JSON);
        if (jsonParsedType != null) supportedEncodings.add(Encoding.JSON_PARSED);
        return supportedEncodings;
    }

    protected abstract RpcRequest constructRequest(Encoding encoding);

    protected RpcRequest build(Encoding encoding) {
        if (encoding != Encoding.NIL && !supportedEncodings.contains(encoding)) {
            throw new UnsupportedOperationException("Unsupported encoding: " + encoding);
        }
        return constructRequest(encoding);
    }

    public D exec() throws RpcException {
        return send(defaultType, Encoding.NIL);
    }

    public B base58() throws RpcException, UnsupportedOperationException {
        return send(baseEncType, Encoding.BASE58);
    }

    public B base64() throws RpcException, UnsupportedOperationException {
        return send(baseEncType, Encoding.BASE64);
    }

    public J json() throws RpcException, UnsupportedOperationException {
        return send(jsonType, Encoding.JSON);
    }

    public P jsonParsed() throws RpcException, UnsupportedOperationException {
        return send(jsonParsedType, Encoding.JSON_PARSED);
    }

    protected <T> T send(TypeReference<T> typeReference, Encoding encoding) throws RpcException {
        return gateway.send(build(encoding), typeReference).getResult();
    }
}
