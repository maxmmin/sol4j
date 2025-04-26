package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.core.crypto.Base58;
import io.github.maxmmin.sol.core.crypto.transaction.CompiledInstruction;

import java.nio.ByteBuffer;
import java.util.List;

public interface MessageSerializer<M> {
    byte[] serialize(M message);

    public static byte[] serialize(Message message) {
        byte[] serializedHeader = serializeMessageHeader(message.getMessageHeader());
        byte[] serializedKeys = serializeAccountKeys(message.getAccountKeys());
        byte[] serializedBlockHash = serializeBlockHash(message.getRecentBlockhash());
        byte[] serializedInstructions = serializeInstructions(message.getInstructions());

        int size = serializedHeader.length + serializedKeys.length + serializedBlockHash.length + serializedInstructions.length;
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put(serializedHeader);
        buffer.put(serializedKeys);
        buffer.put(serializedBlockHash);
        buffer.put(serializedInstructions);
        return buffer.array();
    }

}
