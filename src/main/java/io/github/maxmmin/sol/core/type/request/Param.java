package io.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.maxmmin.sol.core.serialization.ParamSerializer;

@JsonSerialize(using = ParamSerializer.class)
public interface Param<T> {
    T getValue();
}
