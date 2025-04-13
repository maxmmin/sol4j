package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.ShortU16;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompiledInstruction {
    private final byte programIdIndex;
    private final ShortU16 accountsSize;
    private final byte[] accounts;
    private final ShortU16 dataSize;
    private final byte[] data;
}
