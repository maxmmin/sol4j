package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.core.crypto.Base58;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageSerializer;

import java.nio.ByteBuffer;
import java.util.List;

public abstract class AbstractTransactionSerializer<T, M> {
    public static final int SIGNATURE_BYTES = 64;

    private final MessageSerializer<M> messageSerializer;

    public AbstractTransactionSerializer(MessageSerializer<M> messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

    protected abstract List<String> getSignatures(T transaction);

    protected abstract M getMessage(T transaction);

    protected byte[] serializeSignatures(List<String> signatures) {
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

    public byte[] toBytes(T transaction) {
        byte[] signaturesBytes = serializeSignatures(getSignatures(transaction));
        byte[] messageBytes = messageSerializer.serialize(getMessage(transaction));

        byte[] targetBytes = new byte[signaturesBytes.length + messageBytes.length];
        System.arraycopy(signaturesBytes, 0, targetBytes, 0, signaturesBytes.length);
        System.arraycopy(messageBytes, 0, targetBytes, signaturesBytes.length, messageBytes.length);
        return targetBytes;
    }

    public static String toBase58(Transaction transaction) {
        return Base58.encodeToString(toBytes(transaction));
    }
}
