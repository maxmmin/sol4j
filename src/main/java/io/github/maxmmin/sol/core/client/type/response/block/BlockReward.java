package io.github.maxmmin.sol.core.client.type.response.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlockReward {
    private @Nullable BigInteger commission;
    private BigInteger lamports;
    private BigInteger postBalance;
    private String pubkey;
    private String rewardType;
}
