package com.github.maxmmin.sol.core.type.response.epoch;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@Data
public class EpochInfo {
    private BigInteger absoluteSlot;
    private BigInteger blockHeight;
    private BigInteger epoch;
    private BigInteger slotIndex;
    private BigInteger slotsInEpoch;
    private @Nullable BigInteger transactionCount;
}
