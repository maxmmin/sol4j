package io.github.maxmmin.sol.core.client.type.response.node;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Version {
    @JsonProperty("solana-core")
    private String solanaCore;

    @JsonProperty("feature-set")
    private Long featureSet;
}
