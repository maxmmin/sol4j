package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTransactionConfig {
    private final Encoding encoding;
    private final Commitment commitment;
    private final Integer maxSupportedTransactionVersion = 0;

    public static GetTransactionConfig defaultParams() {
        return GetTransactionConfig.builder().build();
    }
}
