package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;

import java.util.List;

@Getter
public class Message {
    private final MessageHeader messageHeader;
    private final List<PublicKey> accountKeys;
    private final String recentBlockhash;
    private final List<CompiledInstruction> instructions;

    public Message(MessageHeader messageHeader, List<PublicKey> accountKeys, String recentBlockhash, List<CompiledInstruction> instructions) {
        this.messageHeader = messageHeader;
        this.accountKeys = List.copyOf(accountKeys);
        this.recentBlockhash = recentBlockhash;
        this.instructions = List.copyOf(instructions);
    }

    public PublicKey getFeePayer() {
        return accountKeys.get(0);
    }
}
