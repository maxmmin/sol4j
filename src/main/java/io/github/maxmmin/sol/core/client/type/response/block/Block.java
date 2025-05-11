package io.github.maxmmin.sol.core.client.type.response.block;

import io.github.maxmmin.sol.core.client.type.response.tx.TxReward;

import java.math.BigInteger;
import java.util.List;

public class Block<T> {
    private BigInteger blockHeight;
    private BigInteger blockTime;
    private String blockhash;
    private BigInteger parentSlot;
    private String previousBlockhash;
    private List<BlockReward> rewards;
    private List<T> transactions;
}
