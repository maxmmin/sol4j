package io.github.maxmmin.sol.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.maxmmin.sol.core.type.request.Param;

import java.io.IOException;

public class ParamSerializer extends JsonSerializer<Param> {
    @Override
    public void serialize(Param param, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (param.getValue() != null) serializerProvider.defaultSerializeValue(param.getValue(), jsonGenerator);
        else jsonGenerator.writeNull();
    }
}