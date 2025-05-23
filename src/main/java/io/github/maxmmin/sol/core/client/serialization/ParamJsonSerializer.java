package io.github.maxmmin.sol.core.client.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.maxmmin.sol.core.client.type.request.Param;

import java.io.IOException;

public class ParamJsonSerializer extends JsonSerializer<Param> {
    @Override
    public void serialize(Param param, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (param.getValue() != null) serializerProvider.defaultSerializeValue(param.getValue(), jsonGenerator);
        else jsonGenerator.writeNull();
    }
}