package io.github.maxmmin.sol.core.client.type.response.epoch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EpochSchedule {
    private BigInteger firstNormalEpoch;
    private BigInteger firstNormalSlot;
    private BigInteger leaderScheduleSlotOffset;
    private BigInteger slotsPerEpoch;
    private boolean warmup;
}
