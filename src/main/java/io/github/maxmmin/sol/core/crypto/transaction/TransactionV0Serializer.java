package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.transaction.message.MessageV0;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageV0Serializer;

public class TransactionV0Serializer implements TransactionSerializer<TransactionV0> {
    private final TransactionComponentsSerializer<MessageV0> componentsSerializer;

    public TransactionV0Serializer() {
        this(new TransactionComponentsSerializer<>(new MessageV0Serializer()));
    }

    public TransactionV0Serializer(TransactionComponentsSerializer<MessageV0> componentsSerializer) {
        this.componentsSerializer = componentsSerializer;
    }

    @Override
    public byte[] serialize(TransactionV0 transaction) {
        byte[] signaturesBytes = componentsSerializer.serializeSignatures(transaction.getSignatures());
        byte[] messageBytes = componentsSerializer.serializeMessage(transaction.getMessage());

        byte[] result = new byte[signaturesBytes.length + messageBytes.length];
        System.arraycopy(signaturesBytes, 0, result, 0, signaturesBytes.length);
        System.arraycopy(messageBytes, 0, result, signaturesBytes.length, messageBytes.length);
        return result;
    }
}
