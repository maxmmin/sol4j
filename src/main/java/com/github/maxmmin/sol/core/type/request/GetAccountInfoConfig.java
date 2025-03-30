package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAccountInfoConfig {
    private final Commitment commitment;
    private final Encoding encoding;
    private final DataSliceConfig dataSliceConfig;
    private final String minContextSlot;

    public static GetAccountInfoConfig empty () {
        return builder().build();
    }
}
