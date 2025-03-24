package org.mxmn.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
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

    @Data
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
