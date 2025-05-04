package io.github.maxmmin.sol.core.client.type.response.block;

import lombok.Data;

import java.math.BigInteger;

@Data
public class LatestBlockhashInfo {
    private String blockhash;
    private BigInteger lastValidBlockHeight;
}
