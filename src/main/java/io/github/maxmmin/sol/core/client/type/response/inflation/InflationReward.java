package io.github.maxmmin.sol.core.client.type.response.inflation;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@Data
public class InflationReward {
    private BigInteger epoch;
    private BigInteger effectiveSlot;
    private BigInteger amount;
    private BigInteger postBalance;
    private @Nullable Integer commission;
}
