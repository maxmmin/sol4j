package com.github.maxmmin.sol.core.client.request.enc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.util.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @param <D> - Default Response Type
 * @param <B> - Base Enc Response Type
 * @param <J> - JSON Response Type
 * @param <P> - JSON Parsed Response Type
 *
 *  Class for types introspection
 */
public abstract class IntrospectedRpcVariety<D, B, J, P> {
    private final RpcTypes<D, B, J, P> rpcTypes;
    private final TypesMetadata typesMetadata;
    private final Set<Encoding> supportedEncodings;

    protected RpcTypes<D, B, J, P> getRpcTypes() {
        return rpcTypes;
    }

    protected TypesMetadata getTypesMetadata() {
        return typesMetadata;
    }

    protected Set<Encoding> getSupportedEncodings() {
        return supportedEncodings;
    }

    public IntrospectedRpcVariety(RpcTypes<D, B, J, P> rpcTypes) {
        this.rpcTypes = rpcTypes;
        this.typesMetadata = introspectTypes(rpcTypes);
        this.supportedEncodings = introspectSupportedEncodings(typesMetadata);
    }

    private TypesMetadata introspectTypes(RpcTypes<D, B, J, P> types) {
        Type relative = types.getClass().getGenericSuperclass();

        if (!(relative instanceof ParameterizedType)) throw new RuntimeException("Expected parameterized type. Actual type: " + relative.getTypeName());
        else if (((ParameterizedType) relative).getRawType().equals(RpcTypes.class)) throw new RuntimeException("Unexpected type: " + relative.getTypeName());

        Type[] typeArguments = ((ParameterizedType) relative).getActualTypeArguments();

        Type defaultType = typeArguments[0];

        Type baseEncType = typeArguments[1];
        if (baseEncType.equals(Void.class)) baseEncType = null;

        Type jsonType = typeArguments[2];
        if (jsonType.equals(Void.class)) jsonType = null;

        Type jsonParsedType = typeArguments[3];
        if (jsonParsedType.equals(Void.class)) jsonParsedType = null;

        return new TypesMetadata(Types.asRef(defaultType), getRef(baseEncType), getRef(jsonType), getRef(jsonParsedType));
    }

    private Set<Encoding> introspectSupportedEncodings(TypesMetadata typeMetadata) {
        Set<Encoding> supportedEncodings = new HashSet<>();
        supportedEncodings.add(Encoding.NIL);

        if (typeMetadata.getBaseEncType() != null) supportedEncodings.addAll(List.of(Encoding.BASE58, Encoding.BASE64));
        if (typeMetadata.getJsonType() != null) supportedEncodings.add(Encoding.JSON);
        if (typeMetadata.getJsonParsedType() != null) supportedEncodings.add(Encoding.JSON_PARSED);

        return supportedEncodings;
    }

    protected <V> TypeReference<V> getRef(@Nullable Type type) {
        return type != null ? Types.asRef(type) : null;
    }

    @Getter
    @RequiredArgsConstructor
    protected class TypesMetadata {
        private final TypeReference<D> defaultType;
        private final TypeReference<B> baseEncType;
        private final TypeReference<J> jsonType;
        private final TypeReference<P> jsonParsedType;
    }

    public static abstract class RpcTypes<D, B, J, P> { }
}
