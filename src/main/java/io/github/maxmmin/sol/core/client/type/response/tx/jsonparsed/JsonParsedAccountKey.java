package io.github.maxmmin.sol.core.client.type.response.tx.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
