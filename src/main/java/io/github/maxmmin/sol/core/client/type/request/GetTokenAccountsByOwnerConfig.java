package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenAccountsByOwnerConfig {
    private final Commitment commitment;
    private final Long minContextSlot;
    private final DataSlice dataSlice;
    private final Encoding encoding;

    public static GetTokenAccountsByOwnerConfig empty() {
        return builder().build();
    }
}
