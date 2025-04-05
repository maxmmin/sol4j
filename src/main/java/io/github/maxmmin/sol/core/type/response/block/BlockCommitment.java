package io.github.maxmmin.sol.core.type.response.block;

import lombok.Data;

import java.math.BigInteger;

@Data
public class BlockCommitment {
    private BigInteger commitment;
    private BigInteger totalStake;
}
