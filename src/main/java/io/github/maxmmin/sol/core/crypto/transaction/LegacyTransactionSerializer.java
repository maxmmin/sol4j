package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.transaction.message.LegacyMessageSerializer;
import io.github.maxmmin.sol.core.crypto.transaction.message.Message;

public class LegacyTransactionSerializer implements TransactionSerializer<Transaction> {
    private final TransactionComponentsSerializer<Message> componentsSerializer;

    public LegacyTransactionSerializer() {
        this(new TransactionComponentsSerializer<>(new LegacyMessageSerializer()));
    }

    public LegacyTransactionSerializer(TransactionComponentsSerializer<Message> componentsSerializer) {
        this.componentsSerializer = componentsSerializer;
    }

    @Override
    public byte[] serialize(Transaction transaction) {
        byte[] signaturesBytes = componentsSerializer.serializeSignatures(transaction.getSignatures());
        byte[] messageBytes = componentsSerializer.serializeMessage(transaction.getMessage());

        byte[] result = new byte[signaturesBytes.length + messageBytes.length];
        System.arraycopy(signaturesBytes, 0, result, 0, signaturesBytes.length);
        System.arraycopy(messageBytes, 0, result, signaturesBytes.length, messageBytes.length);
        return result;
    }
}
