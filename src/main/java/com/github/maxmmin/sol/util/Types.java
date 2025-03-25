package com.github.maxmmin.sol.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

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

    public static JavaType toListType(TypeReference<?> typeReference) {
        return typeFactory.constructCollectionLikeType(List.class, typeFactory.constructType(typeReference));
    }

    public static <V> TypeReference<List<V>> toListRef(TypeReference<V>typeReference) {
        return new TypeReference<List<V>>() {
            @Override
            public Type getType() {
                return toListType(typeReference);
            }
        };
    }
}
