package io.github.maxmmin.sol.core.client.type.response;

import lombok.Data;

import java.math.BigInteger;

@Data
public class BlockRange {
    private BigInteger firstBlock;
    private BigInteger lastBlock;
}
