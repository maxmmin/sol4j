package io.github.maxmmin.sol.core.crypto.transaction;

public interface TransactionSerializer<T> {
    byte[] serialize(T transaction);

    static LegacyTransactionSerializer getSerializer() {
        return new LegacyTransactionSerializer();
    }

    static TransactionV0Serializer getSerializerV0() {
        return new TransactionV0Serializer();
    }
}
