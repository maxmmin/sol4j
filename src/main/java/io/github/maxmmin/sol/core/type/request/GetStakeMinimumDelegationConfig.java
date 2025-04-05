package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetStakeMinimumDelegationConfig {
    private final Commitment commitment;

    public static GetStakeMinimumDelegationConfig empty() {
        return builder().build();
    }
}
