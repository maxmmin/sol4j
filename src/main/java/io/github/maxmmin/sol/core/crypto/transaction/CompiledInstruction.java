package io.github.maxmmin.sol.core.crypto.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompiledInstruction {
    private final byte programIdIndex;
    private final byte[] accounts;
    private final byte[] data;
}
