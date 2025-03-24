package org.mxmn.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LoadedAddresses {
    @JsonProperty("readonly")
    private List<String> readonly;

    @JsonProperty("writable")
    private List<String> writable;
}
