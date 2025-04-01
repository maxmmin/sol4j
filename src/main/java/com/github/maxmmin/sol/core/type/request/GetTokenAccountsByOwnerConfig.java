package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenAccountsByOwnerConfig {
    private final String commitment;
    private final Long minContextSlot;
    private final DataSlice dataSlice;
    private final Encoding encoding;

    public static GetTokenAccountsByOwnerConfig empty() {
        return builder().build();
    }
}
