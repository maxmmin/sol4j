package com.github.maxmmin.sol.core.type.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JsonParsedContent<I> {
    @JsonProperty("info")
    private I info;

    @JsonProperty("type")
    private String type;
}
