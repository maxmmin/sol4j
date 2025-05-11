package io.github.maxmmin.sol.core.client.type.response.tx.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.maxmmin.sol.core.client.type.response.JsonParsedContent;
import io.github.maxmmin.sol.core.client.type.response.JsonParsedData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonParsedInstruction extends JsonParsedData<JsonParsedContent<Map<String, Object>>> {
    @JsonProperty("program")
    private String program;

    @JsonProperty("programId")
    private String programId;

    @JsonProperty("stackHeight")
    private Integer stackHeight;
}
