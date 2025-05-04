package io.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Types {
    private final static TypeFactory typeFactory = TypeFactory.defaultInstance();

    public static <V> TypeReference<V> asRef(Type type) {
        return new TypeReference<V>() {
            @Override
            public Type getType() {
                return type;
            }
        };
    }

    public static JavaType toType(TypeReference<?> typeReference) {
        return typeFactory.constructType(typeReference);
    }

    public static JavaType toParametrizedList(TypeReference<?> contentTypeRef) {
        return toParametrizedList(typeFactory.constructType(contentTypeRef));
    }
    
    public static JavaType toParametrizedList(JavaType contentType) {
        return typeFactory.constructCollectionLikeType(List.class, contentType);
    }

    public static JavaType toParametrizedType(Type rawType, TypeReference<?> typeReference) {
        ParameterizedType parametrized = new ParameterizedType() {
            @Override
            @NotNull
            public Type[] getActualTypeArguments() {
                return new Type[] {typeReference.getType()};
            }

            @NotNull
            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        
        return typeFactory.constructType(parametrized);
    }
}
