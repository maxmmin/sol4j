package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.util.Base58;

import java.nio.ByteBuffer;
import java.util.List;

public class TransactionSerializer {
    public static final int SIGNATURE_BYTES = 64;

    public static byte[] toBytes(Transaction transaction) {
        List<String>signatures = transaction.getSignatures();
        int signaturesLength = signatures.size();
        ShortU16 signaturesLengthU16 = ShortU16.valueOf(signaturesLength);
        byte[] message = MessageSerializer.serialize(transaction.getMessage());

        int txSpace = signaturesLengthU16.getBytesCount() + signaturesLength * SIGNATURE_BYTES + message.length;
        ByteBuffer buffer = ByteBuffer.allocate(txSpace);
        buffer.put(signaturesLengthU16.getValue());
        signatures.forEach(signature -> {
            buffer.put(Base58.decodeFromString(signature));
        });
        buffer.put(message);
        return buffer.array();
    }

    public static String toBase58(Transaction transaction) {
        return Base58.encodeToString(toBytes(transaction));
    }
}
