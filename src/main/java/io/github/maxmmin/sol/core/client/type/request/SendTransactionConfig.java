package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
@Builder
public class SendTransactionConfig {
    private final Encoding encoding;
    private final Boolean skipPreflight;
    private final Boolean preflightCommitment;
    private final Integer maxRetries;
    private final BigInteger minContextSlot;

    public static SendTransactionConfig empty () {
        return builder().build();
    }
}
