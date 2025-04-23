package io.github.maxmmin.sol.core.client.request.enc;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.util.Types;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final RpcTypes<D, B, J , P> rpcTypes;
    private final TypesMetadata typesMetadata;
    private final EncodingSupport encodingSupport;

    public IntrospectedRpcVariety(RpcTypes<D, B, J, P> rpcTypes, EncodingSupport encodingSupport) {
        this.rpcTypes = rpcTypes;
        this.typesMetadata = introspectTypes(rpcTypes);
        this.encodingSupport = encodingSupport;
    }

    protected RpcTypes<D, B, J , P> getRpcTypes() {
        return rpcTypes;
    }

    protected TypesMetadata getTypesMetadata() {
        return typesMetadata;
    }

    public Set<Encoding> getSupportedEncodings() {
        return encodingSupport.getSupportedEncodings();
    }

    public boolean supportsEncoding(Encoding encoding) {
        return encodingSupport.getSupportedEncodings().contains(encoding);
    }

    private TypesMetadata introspectTypes(RpcTypes<D, B, J, P> types) {
        Type relative = types.getClass().getGenericSuperclass();

        if (!(relative instanceof ParameterizedType)) throw new RuntimeException("Expected parameterized type. Actual type: " + relative.getTypeName());
        else if (!((ParameterizedType) relative).getRawType().equals(RpcTypes.class)) throw new RuntimeException("Unexpected type: " + relative.getTypeName());

        Type[] typeArguments = ((ParameterizedType) relative).getActualTypeArguments();

        Type defaultType = typeArguments[0];

        Type baseEncType = typeArguments[1];
        if (baseEncType.equals(Void.class) && (supportsEncoding(Encoding.BASE58) ||
                supportsEncoding(Encoding.BASE64) || supportsEncoding(Encoding.BASE64_ZSTD))) {
            throw new IllegalArgumentException("Base encoding return type must be specified due to specification");
        }

        Type jsonType = typeArguments[2];
        if (jsonType.equals(Void.class) && supportsEncoding(Encoding.JSON)) {
            throw new IllegalArgumentException("Json encoding return type must be specified due to specification");
        }

        Type jsonParsedType = typeArguments[3];
        if (jsonParsedType.equals(Void.class) && supportsEncoding(Encoding.JSON_PARSED)) {
            throw new IllegalArgumentException("Json parsed encoding return type must be specified due to specification");
        }

        return new TypesMetadata(Types.asRef(defaultType), getRef(baseEncType), getRef(jsonType), getRef(jsonParsedType));
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

    @Getter
    public static class EncodingSupport {
        private final Set<Encoding> supportedEncodings;

        public EncodingSupport(Encoding... encodings) {
            this.supportedEncodings = Arrays.stream(encodings).collect(Collectors.toSet());
        }

        public static EncodingSupport baseOnly() {
            return new EncodingSupport(Encoding.BASE58, Encoding.BASE64);
        }

        public static EncodingSupport baseWithCompressing() {
            return new EncodingSupport(Encoding.BASE58, Encoding.BASE64, Encoding.BASE64);
        }

        public static EncodingSupport full() {
            var encodings = Arrays.stream(Encoding.values())
                    .filter(e -> e != Encoding.BASE64_ZSTD)
                    .toArray(Encoding[]::new);

            return new EncodingSupport(encodings);
        }

        public static EncodingSupport fullWithCompressing() {
            return new EncodingSupport(Encoding.values());
        }
    }
}
