package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenAccountBalanceConfig {
    private final Commitment commitment;

    public static GetTokenAccountBalanceConfig empty() {
        return builder().build();
    }
}
