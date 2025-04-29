package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Base58;
import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageSerializer;

import java.nio.ByteBuffer;
import java.util.List;

public class TransactionComponentsSerializer<M> {
    public static final int SIGNATURE_BYTES = 64;

    private final MessageSerializer<M> messageSerializer;

    public TransactionComponentsSerializer(MessageSerializer<M> messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

    public byte[] serializeSignatures(List<String> signatures) {
        int signaturesLength = signatures.size();
        ShortU16 signaturesLengthU16 = ShortU16.valueOf(signaturesLength);

        int signaturesSpace = signaturesLengthU16.getBytesCount() + signaturesLength * SIGNATURE_BYTES;
        ByteBuffer buffer = ByteBuffer.allocate(signaturesSpace);
        buffer.put(signaturesLengthU16.getValue());
        signatures.forEach(signature -> {
            buffer.put(Base58.decodeFromString(signature));
        });
        return buffer.array();
    }

    public byte[] serializeMessage(M message) {
        return messageSerializer.serialize(message);
    }
}
