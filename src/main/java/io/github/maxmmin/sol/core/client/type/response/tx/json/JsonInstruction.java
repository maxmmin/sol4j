package io.github.maxmmin.sol.core.client.type.response.tx.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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