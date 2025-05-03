package io.github.maxmmin.sol.core.client.type.response.epoch;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EpochSchedule {
    private BigInteger firstNormalEpoch;
    private BigInteger firstNormalSlot;
    private BigInteger leaderScheduleSlotOffset;
    private BigInteger slotsPerEpoch;
    private boolean warmup;
}
