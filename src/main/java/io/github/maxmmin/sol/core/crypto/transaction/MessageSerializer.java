package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.util.Base58;
import io.github.maxmmin.sol.util.ShortU16Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class MessageSerializer {
    private final Message message;

    public MessageSerializer(Message message) {
        this.message = message;
    }

    public byte[] serialize() {
        byte[] serializedHeader = serializeMessageHeader(message.getMessageHeader());
        byte[] serializedKeys = serializeAccountKeys(message.getAccountKeys());
        byte[] serializedBlockHash = serializeBlockHash(message.getRecentBlockhash());
    }

    private byte[] serializeMessageHeader(MessageHeader messageHeader) {
        return new byte[] {messageHeader.getNumRequiredSignatures(),messageHeader.getNumReadonlySignedAccounts(), messageHeader.getNumReadonlyUnsignedAccounts()}
    }

    private byte[] serializeAccountKeys(List<PublicKey> accountKeys) {
        ShortU16 accountsLength = ShortU16Util.serialize(accountKeys.size());
        int outputSize = accountsLength.getBytesCount() + accountKeys.size() * PUBLIC_KEY_BYTES;
        ByteBuffer buffer = ByteBuffer.allocate(outputSize);
        buffer.put(accountsLength.getValue());
        for (PublicKey accountKey : accountKeys) {
            buffer.put(accountKey.getBytes());
        }
        return buffer.array();
    }

    private byte[] serializeBlockHash(String blockHash) {
        byte[] hashBytes = Base58.decode(blockHash.getBytes());
        if (hashBytes.length != 32) throw new IllegalArgumentException("Invalid block hash");
        return hashBytes;
    }

    protected int calculateInstructionSize(CompiledInstruction instruction) {
        return 1 + instruction.getAccountsSizeU16().getBytesCount() + instruction.getAccounts().length
                + instruction.getDataSizeU16().getBytesCount() + instruction.getData().length;
    }

    public static final int HEADER_BYTES = 3;
    public static final int SIGNATURE_BYTES = 64;
    public static final int BLOCKHASH_BYTES = 32;
    public static final int PUBLIC_KEY_BYTES = 32;
}
