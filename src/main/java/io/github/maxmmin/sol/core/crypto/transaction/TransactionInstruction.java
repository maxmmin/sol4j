package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TransactionInstruction {
    private final PublicKey programId;
    private final List<AccountMeta> accounts;
    private final byte[] data;
}
