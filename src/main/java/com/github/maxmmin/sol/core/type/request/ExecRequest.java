package com.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.util.Types;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * interface for types introspection
 */
interface RpcVariety<D, B, J, P> {
    D noarg() throws RpcException;
    B base58() throws RpcException, UnsupportedOperationException;
    B base64() throws RpcException, UnsupportedOperationException;
    J json() throws RpcException, UnsupportedOperationException;
    P jsonParsed() throws RpcException, UnsupportedOperationException;
}

/**
 *
 * @param <D> - Default Response Type
 * @param <B> - Base Enc Response Type
 * @param <J> - JSON Response Type
 * @param <P> - JSON Parsed Response Type
 */
public abstract class ExecRequest<D, B, J, P> implements RpcVariety<D, B, J, P> {
    private final RpcGateway gateway;

    private final TypesMetadata typesMetadata;
    private final Set<Encoding> supportedEncodings;

    public ExecRequest(RpcGateway gateway) {
        this.gateway = gateway;
        this.typesMetadata = introspectTypes();
        this.supportedEncodings = introspectSupportedEncodings(typesMetadata);
    }

    private TypesMetadata introspectTypes() {
        Type[] genericTypes = getClass().getSuperclass().getGenericInterfaces();
        Type defaultType = genericTypes[0];

        Type baseEncType = genericTypes[1];
        if (baseEncType.equals(Object.class)) baseEncType = null;

        Type jsonType = genericTypes[2];
        if (jsonType.equals(Object.class)) jsonType = null;

        Type jsonParsedType = genericTypes[3];
        if (jsonParsedType.equals(Object.class)) jsonParsedType = null;

        return new TypesMetadata(Types.asRef(defaultType), getRef(baseEncType), getRef(jsonType), getRef(jsonParsedType));
    }

    protected <V> TypeReference<V> getRef(@Nullable Type type) {
        return type != null ? Types.asRef(type) : null;
    }

    private Set<Encoding> introspectSupportedEncodings(TypesMetadata typeMetadata) {
        Set<Encoding> supportedEncodings = new HashSet<>();
        supportedEncodings.add(Encoding.NIL);

        if (typeMetadata.getBaseEncType() != null) supportedEncodings.addAll(List.of(Encoding.BASE58, Encoding.BASE64));
        if (typeMetadata.getJsonType() != null) supportedEncodings.add(Encoding.JSON);
        if (typeMetadata.getJsonParsedType() != null) supportedEncodings.add(Encoding.JSON_PARSED);

        return supportedEncodings;
    }

    protected abstract RpcRequest constructRequest(Encoding encoding);

    @Override
    public D noarg() throws RpcException {
        return send(typesMetadata.getDefaultType(), Encoding.NIL);
    }

    @Override
    public B base58() throws RpcException, UnsupportedOperationException {
        return send(typesMetadata.getBaseEncType(), Encoding.BASE58);
    }

    @Override
    public B base64() throws RpcException, UnsupportedOperationException {
        return send(typesMetadata.getBaseEncType(), Encoding.BASE64);
    }

    @Override
    public J json() throws RpcException, UnsupportedOperationException {
        return send(typesMetadata.getJsonType(), Encoding.JSON);
    }

    @Override
    public P jsonParsed() throws RpcException, UnsupportedOperationException {
        return send(typesMetadata.getJsonParsedType(), Encoding.JSON_PARSED);
    }

    protected <T> T send(TypeReference<T> typeReference, Encoding encoding) throws RpcException {
        if (encoding != Encoding.NIL && !supportedEncodings.contains(encoding)) {
            throw new UnsupportedOperationException("Unsupported encoding: " + encoding);
        }
        return gateway.send(constructRequest(encoding), typeReference).getResult();
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    class TypesMetadata {
        private final TypeReference<D> defaultType;
        private final TypeReference<B> baseEncType;
        private final TypeReference<J> jsonType;
        private final TypeReference<P> jsonParsedType;
    }
}
