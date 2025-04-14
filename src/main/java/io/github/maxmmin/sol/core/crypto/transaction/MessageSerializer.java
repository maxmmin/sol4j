package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;
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
        int txSize = 0;

        List<PublicKey> accounts = message.getAccountKeys();
        ShortU16 accountsLength = ShortU16Util.serialize(accounts.size());
        txSize += accountsLength.getBytesCount();
        txSize += (accounts.size() * PUBLIC_KEY_BYTES);

        for (CompiledInstruction instruction : message.getInstructions()) {
            txSize += calculateInstructionSize(instruction);
        }

    }

    protected byte[] serializeAccountKeys(List<PublicKey> accountKeys) {
        ShortU16 accountsLength = ShortU16Util.serialize(accountKeys.size());
        int outputSize = accountsLength.getBytesCount() + accountKeys.size() * PUBLIC_KEY_BYTES;
        ByteBuffer buffer = ByteBuffer.allocate(outputSize);
        buffer.put(accountsLength.getValue());
        for (PublicKey accountKey : accountKeys) {
            buffer.put(accountKey.getBytes());
        }
        return buffer.array();
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
