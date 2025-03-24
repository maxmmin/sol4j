package com.github.maxmmin.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Reward {
    @JsonProperty("pubkey")
    private String pubkey;

    @JsonProperty("lamports")
    private BigInteger lamports;

    @JsonProperty("postBalance")
    private BigInteger postBalance;

    @JsonProperty("rewardType")
    private String rewardType;

    @JsonProperty("comission")
    private Integer commission;
}
