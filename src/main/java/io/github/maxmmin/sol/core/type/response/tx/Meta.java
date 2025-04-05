package io.github.maxmmin.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
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
    private List<? extends Reward> rewards;
}

