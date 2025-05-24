package io.github.maxmmin.sol.core.client.type.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @param <I> - Info data type
 *              Represents parsed entity content.
 *              Contains data in {@link JsonParsedContent#raw} if content was unparsed and no data in other fields
 *              and opposite state if it was successfully parsed
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonParsedContent<I> {
    private @Nullable String raw;

    @JsonProperty("info")
    private @Nullable I info;

    @JsonProperty("type")
    private @Nullable String type;

    public JsonParsedContent(@NotNull String rawData) {
        this.raw = rawData;
    }

    public boolean isParsed() {
        return this.getRaw() != null;
    }
}
