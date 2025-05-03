package io.github.maxmmin.sol.core.client.type.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.maxmmin.sol.core.client.serialization.ParamJsonSerializer;

@JsonSerialize(using = ParamJsonSerializer.class)
public interface Param<T> {
    T getValue();
}
