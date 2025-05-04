package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.Base58;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.core.crypto.transaction.CompiledInstruction;

import java.nio.ByteBuffer;
import java.util.List;

public class MessageComponentsSerializer {
    public static final int BLOCKHASH_BYTES = 32;
    public static final int PUBLIC_KEY_BYTES = 32;

    public byte[] serializeMessageHeader(MessageHeader messageHeader) {
        return new byte[] {messageHeader.getNumRequiredSignatures(), messageHeader.getNumReadonlySignedAccounts(), messageHeader.getNumReadonlyUnsignedAccounts()};
    }

    public byte[] serializeAccountKeys(List<PublicKey> accountKeys) {
        ShortU16 accountsLength = ShortU16.valueOf(accountKeys.size());
        int outputSize = accountsLength.getBytesCount() + accountKeys.size() * PUBLIC_KEY_BYTES;
        ByteBuffer buffer = ByteBuffer.allocate(outputSize);
        buffer.put(accountsLength.getValue());
        for (PublicKey accountKey : accountKeys) {
            buffer.put(accountKey.getBytes());
        }
        return buffer.array();
    }

    public byte[] serializeBlockHash(String blockHash) {
        byte[] hashBytes = Base58.decode(blockHash.getBytes());
        if (hashBytes.length != BLOCKHASH_BYTES) throw new IllegalArgumentException("Invalid block hash");
        return hashBytes;
    }

    public byte[] serializeInstructions(List<CompiledInstruction> compiledInstructions) {
        int instructionsSpace = 0;

        ShortU16 instructionsCount = ShortU16.valueOf(compiledInstructions.size());
        instructionsSpace += instructionsCount.getBytesCount();

        for (CompiledInstruction compiledInstruction : compiledInstructions) {
            instructionsSpace += calculateInstructionSize(compiledInstruction);
        }

        ByteBuffer buffer = ByteBuffer.allocate(instructionsSpace);
        buffer.put(instructionsCount.getValue());
        for (CompiledInstruction compiledInstruction : compiledInstructions) {
            buffer.put(compiledInstruction.getProgramIdIndex());
            buffer.put(compiledInstruction.getAccountsSizeU16().getValue());
            buffer.put(compiledInstruction.getAccounts());
            buffer.put(compiledInstruction.getDataSizeU16().getValue());
            buffer.put(compiledInstruction.getData());
        }
        return buffer.array();
    }

    private static int calculateInstructionSize(CompiledInstruction instruction) {
        return 1 + instruction.getAccountsSizeU16().getBytesCount() + instruction.getAccounts().length
                + instruction.getDataSizeU16().getBytesCount() + instruction.getData().length;
    }
}
