package io.github.maxmmin.sol.core.type.response.tx.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import io.github.maxmmin.sol.core.type.response.JsonParsedContent;
import io.github.maxmmin.sol.core.type.response.JsonParsedData;

import java.util.Map;

@Data
public class JsonParsedInstruction extends JsonParsedData<JsonParsedContent<Map<String, Object>>> {
    @JsonProperty("program")
    private String program;

    @JsonProperty("programId")
    private String programId;

    @JsonProperty("stackHeight")
    private Integer stackHeight;
}
