package io.github.maxmmin.sol.core.client.type.response.tx.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JsonInstruction {
    @JsonProperty("accounts")
    private List<Integer> accounts;

    @JsonProperty("data")
    private String data;

    @JsonProperty("programIdIndex")
    private Integer programIdIndex;

    @JsonProperty("stackHeight")
    private Integer stackHeight;
}