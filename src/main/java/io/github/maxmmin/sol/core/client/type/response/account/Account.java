package io.github.maxmmin.sol.core.client.type.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
