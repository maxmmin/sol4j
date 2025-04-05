package io.github.maxmmin.sol.core.type.response.block;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@Data
public class HighestSnapshotSlot {
    private BigInteger full;
    private @Nullable BigInteger incremental;
}
