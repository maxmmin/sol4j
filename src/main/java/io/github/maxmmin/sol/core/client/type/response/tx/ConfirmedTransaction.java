package io.github.maxmmin.sol.core.client.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedTransaction<T, I> {
    @JsonProperty("meta")
    private Meta<I> meta;

    @JsonProperty("transaction")
    private T transaction;

    @JsonProperty("slot")
    private BigInteger slot;

    @JsonProperty("blockTime")
    private Integer blockTime;
}
