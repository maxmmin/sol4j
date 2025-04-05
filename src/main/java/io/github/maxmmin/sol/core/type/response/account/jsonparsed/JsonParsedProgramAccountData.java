package io.github.maxmmin.sol.core.type.response.account.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.maxmmin.sol.core.type.response.JsonParsedContent;
import io.github.maxmmin.sol.core.type.response.JsonParsedData;
import lombok.Data;

@Data
public class JsonParsedProgramAccountData<P extends JsonParsedContent<?>> extends JsonParsedData<P> {
    @JsonProperty("space")
    private Integer space;
}
