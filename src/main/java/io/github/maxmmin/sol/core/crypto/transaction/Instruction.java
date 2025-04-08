package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Instruction {
    private final PublicKey programId;
    private final List<AccountMeta> accounts;
    private final List<Integer> data;
}
