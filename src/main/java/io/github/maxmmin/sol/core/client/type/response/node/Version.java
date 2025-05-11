package io.github.maxmmin.sol.core.client.type.response.node;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Version {
    @JsonProperty("solana-core")
    private String solanaCore;

    @JsonProperty("feature-set")
    private Long featureSet;
}
