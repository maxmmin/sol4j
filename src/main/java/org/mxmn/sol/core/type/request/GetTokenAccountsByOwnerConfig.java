package org.mxmn.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenAccountsByOwnerConfig {
    private final String commitment;
    private final Long minContextSlot;
    private final DataSliceConfig dataSlice;
    private final Encoding encoding;
}
