package com.github.maxmmin.sol.core.type.response.tx.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JsonParsedAccountKey {
    @JsonProperty("pubkey")
    private String pubkey;

    @JsonProperty("signer")
    private Boolean signer;

    @JsonProperty("source")
    private String source;

    @JsonProperty("writable")
    private Boolean writable;
}
