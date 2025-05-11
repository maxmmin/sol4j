package io.github.maxmmin.sol.core.client.type.response.inflation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InflationReward {
    private BigInteger epoch;
    private BigInteger effectiveSlot;
    private BigInteger amount;
    private BigInteger postBalance;
    private @Nullable Integer commission;
}
