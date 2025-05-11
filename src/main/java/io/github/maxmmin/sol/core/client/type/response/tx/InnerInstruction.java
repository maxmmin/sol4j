package io.github.maxmmin.sol.core.client.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InnerInstruction<I> {
    @JsonProperty("index")
    private Integer index;

    @JsonProperty("instructions")
    private List<I> instructions;
}