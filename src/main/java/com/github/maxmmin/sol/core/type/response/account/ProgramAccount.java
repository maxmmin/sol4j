package com.github.maxmmin.sol.core.type.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProgramAccount<D extends AccountDetails<?>> {
    @JsonProperty("account")
    private D account;

    @JsonProperty("pubkey")
    private String pubkey;
}
