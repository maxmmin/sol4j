package io.github.maxmmin.sol.core.client.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoadedAddresses {
    @JsonProperty("readonly")
    private List<String> readonly;

    @JsonProperty("writable")
    private List<String> writable;
}
