package io.github.maxmmin.sol.core.client.type.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonParsedData<V extends JsonParsedContent<?>> {
    @JsonProperty("parsed")
    private V parsed;

    @JsonProperty("program")
    private String program;

    // fallback field
    @JsonProperty("data")
    private @Nullable String data;
}
