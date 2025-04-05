package io.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetMultipleAccountsConfig {
    private final Encoding encoding;
    private final Commitment commitment;
    private final DataSlice dataSlice;

    public static GetMultipleAccountsConfig empty() {
        return builder().build();
    }
}
