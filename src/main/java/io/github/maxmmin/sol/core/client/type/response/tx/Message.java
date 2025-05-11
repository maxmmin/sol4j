package io.github.maxmmin.sol.core.client.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message<K, I> {
    @JsonProperty("accountKeys")
    private List<K> accountKeys;

    @JsonProperty("header")
    private Map<String, Object> header;

    @JsonProperty("instructions")
    private List<? extends I> instructions;

    @JsonProperty("recentBlockhash")
    private String recentBlockhash;
}
