package io.github.maxmmin.sol.core.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountMeta {
    private final PublicKey pubkey;
    private final boolean isSigner;
    private final boolean isWritable;
}
