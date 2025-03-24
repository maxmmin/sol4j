package com.github.maxmmin.sol.core.type.response.account.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.github.maxmmin.sol.core.type.response.JsonParsedContent;
import com.github.maxmmin.sol.core.type.response.JsonParsedData;

@Data
public class JsonParsedProgramAccountData<P extends JsonParsedContent<?>> extends JsonParsedData<P> {
    @JsonProperty("space")
    private Integer space;
}
