package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetProgramAccountsConfig {
    private final Encoding encoding;
    private final List<?>filters;
    private final Commitment commitment;
    private final DataSliceConfig dataSlice;
}
