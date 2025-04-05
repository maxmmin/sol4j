package io.github.maxmmin.sol.core.type.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Account<D> {
    @JsonProperty("data")
    private D data;

    @JsonProperty("executable")
    private Boolean executable;

    @JsonProperty("lamports")
    private BigInteger lamports;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("rentEpoch")
    private BigInteger rentEpoch;

    @JsonProperty("space")
    private Integer space;
}
