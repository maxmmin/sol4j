package io.github.maxmmin.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class InnerInstruction<I> {
    @JsonProperty("index")
    private Integer index;

    @JsonProperty("instructions")
    private List<I> instructions;
}