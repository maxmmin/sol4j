package org.mxmn.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetTokenAccountsByOwnerParams {
    private final String mint;
    private final String programId;
}
