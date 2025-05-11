package io.github.maxmmin.sol.core.client.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Meta<I> {
    @JsonProperty("computeUnitsConsumed")
    private int computeUnitsConsumed;

    @JsonProperty("err")
    private Object err;

    @JsonProperty("fee")
    private BigInteger fee;

    @JsonProperty("innerInstructions")
    private List<? extends InnerInstruction<I>> innerInstructions;

    @JsonProperty("loadedAddresses")
    private LoadedAddresses loadedAddresses;

    @JsonProperty("logMessages")
    private List<String> logMessages;

    @JsonProperty("postBalances")
    private List<BigInteger> postBalances;

    @JsonProperty("postTokenBalances")
    private List<? extends TokenBalance> postTokenBalances;

    @JsonProperty("preBalances")
    private List<BigInteger> preBalances;

    @JsonProperty("preTokenBalances")
    private List<? extends TokenBalance> preTokenBalances;

    @JsonProperty("rewards")
    private List<? extends TxReward> rewards;
}

