package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetSignaturesForAddressConfig {
    private final Commitment commitment;
    private final Long minContextSlot;
    private final String before;
    private final String until;
    private final Integer limit;
    private final Long untilTime;

    public static GetSignaturesForAddressConfig empty() {
        return builder().build();
    }
}
