package io.github.maxmmin.sol.core.crypto.transaction.message;

public interface MessageSerializer<M> {
    byte[] serialize(M message);

    static LegacyMessageSerializer getSerializer() {
        return new LegacyMessageSerializer();
    }

    static MessageV0Serializer getSerializerV0() {
        return new MessageV0Serializer();
    }
}
