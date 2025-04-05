package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetBlockProductionConfig {
    private final Commitment commitment;
    private final String identity;
    private final BlockRange range;

    public static GetBlockProductionConfig empty() {
        return builder().build();
    }
}
