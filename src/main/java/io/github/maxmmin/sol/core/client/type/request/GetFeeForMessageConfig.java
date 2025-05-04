package io.github.maxmmin.sol.core.client.type.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@Builder
@RequiredArgsConstructor
public class GetFeeForMessageConfig {
    private final Commitment commitment;
    private final BigInteger minContextSlot;

    public static GetFeeForMessageConfig empty () {
        return GetFeeForMessageConfig.builder().build();
    }
}
