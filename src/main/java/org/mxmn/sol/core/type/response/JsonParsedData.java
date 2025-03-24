package org.mxmn.sol.core.type.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class JsonParsedData<V extends JsonParsedContent<?>> {
    @JsonProperty("parsed")
    private V parsed;

    @JsonProperty("program")
    private String program;

    // fallback field
    @JsonProperty("data")
    private @Nullable String data;
}
