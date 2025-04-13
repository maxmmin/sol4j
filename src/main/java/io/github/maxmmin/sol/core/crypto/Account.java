package io.github.maxmmin.sol.core.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Account {
    private final PublicKey publicKey;
}
