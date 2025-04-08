package io.github.maxmmin.sol.core.crypto.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageHeader {
    private final Integer numRequiredSignatures;
    private final Integer numReadonlySignedAccounts;
    private final Integer numReadonlyUnsignedAccounts;
}
