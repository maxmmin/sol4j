package io.github.maxmmin.sol.core.client.type.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramAccount<D extends Account<?>> {
    @JsonProperty("account")
    private D account;

    @JsonProperty("pubkey")
    private String pubkey;
}
