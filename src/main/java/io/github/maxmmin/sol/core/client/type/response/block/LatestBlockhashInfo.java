package io.github.maxmmin.sol.core.client.type.response.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LatestBlockhashInfo {
    private String blockhash;
    private BigInteger lastValidBlockHeight;
}
