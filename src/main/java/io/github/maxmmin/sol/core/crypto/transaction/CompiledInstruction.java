package io.github.maxmmin.sol.core.crypto.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CompiledInstruction {
    private final Integer programIdIndex;
    private final List<Integer> accounts;
    private final byte[] data;
}
