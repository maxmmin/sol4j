package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenLargestAccountsConfig {
    private final Commitment commitment;

    public static GetTokenLargestAccountsConfig empty() {
        return builder().build();
    }
}
