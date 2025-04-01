package com.github.maxmmin.sol.core.type.response.block;

import lombok.Data;

import java.math.BigInteger;

@Data
public class LatestBlockhashInfo {
    private String blockhash;
    private BigInteger lastValidBlockHeight;
}
