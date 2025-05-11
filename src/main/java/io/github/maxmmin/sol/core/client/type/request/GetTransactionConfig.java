package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTransactionConfig {
    private final Commitment commitment;
    private final Integer maxSupportedTransactionVersion;

    public static GetTransactionConfig empty() {
        return builder().build();
    }
}
