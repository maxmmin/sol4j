package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTransactionCountConfig {
    private final BigInteger minContextSlot;
    private final Commitment commitment;

    public static GetTransactionCountConfig empty() {
        return builder().build();
    }
}
