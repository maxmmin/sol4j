package io.github.maxmmin.sol.core.client.type.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProgramAccount<D extends Account<?>> {
    @JsonProperty("account")
    private D account;

    @JsonProperty("pubkey")
    private String pubkey;
}
