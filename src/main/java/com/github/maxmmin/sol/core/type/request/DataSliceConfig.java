package com.github.maxmmin.sol.core.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DataSliceConfig {
    private final Integer length;
    private final Integer offset;

    public static DataSliceConfig emptySlice() {
        return DataSliceConfig.builder().length(0).offset(0).build();
    }
}
