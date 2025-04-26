package io.github.maxmmin.sol.core.crypto.transaction.message;

public interface MessageSerializer<M> {
    byte[] serialize(M message);

    static MessageV0Serializer getV0Serializer() {
        return new MessageV0Serializer();
    }

    static LegacyMessageSerializer getLegacySerializer() {
        return new LegacyMessageSerializer();
    }
}
