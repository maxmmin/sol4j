package io.github.maxmmin.sol.core.client.type.response.epoch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EpochInfo {
    private BigInteger absoluteSlot;
    private BigInteger blockHeight;
    private BigInteger epoch;
    private BigInteger slotIndex;
    private BigInteger slotsInEpoch;
    private @Nullable BigInteger transactionCount;
}
