package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenAccountsByDelegateConfig {
    private final Commitment commitment;
    private final BigInteger minContextSlot;
    private final DataSlice dataSlice;
    private final Encoding encoding;

    public static GetTokenAccountsByDelegateConfig empty () {
        return builder().build();
    }
}
