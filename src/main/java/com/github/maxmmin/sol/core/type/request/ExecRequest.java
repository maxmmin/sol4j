package com.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.BiConsumer;

/**
 *
 * @param <D> - Default Response Type
 * @param <B> - Base Enc Response Type
 * @param <J> - JSON Response Type
 * @param <P> - JSON Parsed Response Type
 */
public class ExecRequest<D, B, J, P> {
    private final RpcRequest request;
    private final RpcGateway gateway;

    private final TypeReference<D> defaultType;
    private final @Nullable TypeReference<B> baseEncType;
    private final @Nullable TypeReference<J> jsonType;
    private final @Nullable TypeReference<P> jsonParsedType;
    private final @Nullable BiConsumer<RpcRequest, Encoding> encWriter;
    private final Set<Encoding> supportedEncodings;

    public ExecRequest(RpcRequest request, RpcGateway gateway, TypeReference<D> defaultType) {
        this(request, gateway, defaultType, null, null, null, null, Set.of());
    }

    public ExecRequest(RpcRequest request, RpcGateway gateway, TypeReference<D> defaultType,
                       @Nullable TypeReference<B> baseEncType, @Nullable TypeReference<J> jsonType, @Nullable TypeReference<P> jsonParsedType,
                       @Nullable BiConsumer<RpcRequest, Encoding> encWriter, Set<Encoding> supportedEncodings) {
        this.request = request;
        this.gateway = gateway;
        this.encWriter = encWriter;
        this.supportedEncodings = supportedEncodings;
        this.defaultType = defaultType;
        this.baseEncType = baseEncType;
        this.jsonType = jsonType;
        this.jsonParsedType = jsonParsedType;
    }

    protected void applyEncoding(Encoding encoding) {
        if (encoding != Encoding.NIL) {
            if (supportedEncodings.contains(encoding)) {
                assert encWriter != null;
                encWriter.accept(request, encoding);
            } else throw new UnsupportedOperationException("Unsupported encoding: " + encoding);
        }
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

    protected <T> T send(TypeReference<T> typeReference, @Nullable Encoding encoding) throws RpcException {
        if (encoding != null) applyEncoding(encoding);
        return gateway.send(request, typeReference).getResult();
    }
}
