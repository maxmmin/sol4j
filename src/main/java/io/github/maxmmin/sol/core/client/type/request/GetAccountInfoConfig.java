package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAccountInfoConfig {
    private final Commitment commitment;
    private final Encoding encoding;
    private final DataSlice dataSlice;
    private final String minContextSlot;

    public static GetAccountInfoConfig empty () {
        return builder().build();
    }
}
