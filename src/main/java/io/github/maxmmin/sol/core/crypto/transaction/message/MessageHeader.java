package io.github.maxmmin.sol.core.crypto.transaction.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageHeader {
    private final byte numRequiredSignatures;
    private final byte numReadonlySignedAccounts;
    private final byte numReadonlyUnsignedAccounts;
}
