package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
@Builder
public class GetBalanceConfig {
    private final Commitment commitment;
    private final BigInteger minContextSlot;

    public static GetBalanceConfig empty() {
        return builder().build();
    }
}
