package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.CompiledInstruction;
import lombok.Getter;

import java.util.List;

@Getter
public class MessageV0 {
    private final MessageHeader messageHeader;
    private final List<PublicKey> accountKeys;
    private final String recentBlockhash;
    private final List<CompiledInstruction> instructions;
    private final List<MessageAddressTableLookup> accountKeysLookups;

    public MessageV0(MessageHeader messageHeader, List<PublicKey> accountKeys,
                     String recentBlockhash, List<CompiledInstruction> instructions,
                     List<MessageAddressTableLookup> accountKeysLookups) {
        this.messageHeader = messageHeader;
        this.accountKeys = List.copyOf(accountKeys);
        this.recentBlockhash = recentBlockhash;
        this.instructions = List.copyOf(instructions);
        this.accountKeysLookups = List.copyOf(accountKeysLookups);
    }

    public PublicKey getFeePayer() {
        return accountKeys.get(0);
    }
}
