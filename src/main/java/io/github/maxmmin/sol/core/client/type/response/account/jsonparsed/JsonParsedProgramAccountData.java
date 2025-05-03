package io.github.maxmmin.sol.core.client.type.response.account.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.maxmmin.sol.core.client.type.response.JsonParsedContent;
import io.github.maxmmin.sol.core.client.type.response.JsonParsedData;
import lombok.Data;

@Data
public class JsonParsedProgramAccountData<P extends JsonParsedContent<?>> extends JsonParsedData<P> {
    @JsonProperty("space")
    private Integer space;
}
