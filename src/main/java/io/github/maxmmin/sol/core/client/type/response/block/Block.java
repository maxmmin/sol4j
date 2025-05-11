package io.github.maxmmin.sol.core.client.type.response.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Block<T> {
    private BigInteger blockHeight;
    private BigInteger blockTime;
    private String blockhash;
    private BigInteger parentSlot;
    private String previousBlockhash;
    private List<BlockReward> rewards;
    private List<T> transactions;
}
