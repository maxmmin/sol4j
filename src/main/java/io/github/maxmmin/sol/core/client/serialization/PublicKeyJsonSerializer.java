package io.github.maxmmin.sol.core.client.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.maxmmin.sol.core.crypto.PublicKey;

import java.io.IOException;

public class PublicKeyJsonSerializer extends JsonSerializer<PublicKey> {
    @Override
    public void serialize(PublicKey publicKey, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(publicKey.toBase58());
    }
}
