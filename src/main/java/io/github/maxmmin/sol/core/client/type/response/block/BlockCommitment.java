package io.github.maxmmin.sol.core.client.type.response.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlockCommitment {
    private BigInteger commitment;
    private BigInteger totalStake;
}
