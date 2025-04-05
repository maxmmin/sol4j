package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@Builder
@RequiredArgsConstructor
public class GetLatestBlockhashConfig {
    private final Commitment commitment;
    private final BigInteger minContextSlot;

    public static GetLatestBlockhashConfig empty() {
        return builder().build();
    }
}
