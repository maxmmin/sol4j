package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Message {
    private final MessageHeader messageHeader;
    private final List<PublicKey> accountKeys;
    private final String recentBlockhash;
    private final List<CompiledInstruction> instructions;
}
