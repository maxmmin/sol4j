package org.mxmn.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Transaction<T, I> {
    @JsonProperty("meta")
    private Meta<I> meta;

    @JsonProperty("transaction")
    private T transaction;

    @JsonProperty("slot")
    private BigInteger slot;

    @JsonProperty("blockTime")
    private Integer blockTime;
}
