package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.CompiledInstruction;

import java.util.List;

public class MessageV0 {
    private final MessageHeader messageHeader;
    private final List<PublicKey> accountKeys;
    private final String recentBlockhash;
    private final List<CompiledInstruction> instructions;
    // @todo LookupTables

    public MessageV0(MessageHeader messageHeader, List<PublicKey> accountKeys, String recentBlockhash, List<CompiledInstruction> instructions) {
        this.messageHeader = messageHeader;
        this.accountKeys = List.copyOf(accountKeys);
        this.recentBlockhash = recentBlockhash;
        this.instructions = List.copyOf(instructions);
    }

    public PublicKey getFeePayer() {
        return accountKeys.get(0);
    }
}
