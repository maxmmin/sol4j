package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@Builder
@RequiredArgsConstructor
public class GetInflationRewardConfig {
    private final Commitment commitment;
    private final BigInteger epoch;
    private final BigInteger minContextSlot;

    public static GetInflationRewardConfig empty () {
        return builder().build();
    }
}
