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
public class TokenBalance {
    @JsonProperty("accountIndex")
    private Integer accountIndex;

    @JsonProperty("mint")
    private String mint;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("programId")
    private String programId;

    @JsonProperty("uiTokenAmount")
    private UiTokenAmount uiTokenAmount;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UiTokenAmount{
        @JsonProperty("amount")
        private BigInteger amount;

        @JsonProperty("decimals")
        private Integer decimals;

        @JsonProperty("uiAmount")
        private double uiAmount;

        @JsonProperty("uiAmountString")
        private String uiAmountString;
    }
}
