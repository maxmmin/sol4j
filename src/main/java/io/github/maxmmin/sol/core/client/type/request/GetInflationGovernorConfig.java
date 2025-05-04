package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetInflationGovernorConfig {
    private final Commitment commitment;

    public static GetInflationGovernorConfig empty() {
        return builder().build();
    }
}
