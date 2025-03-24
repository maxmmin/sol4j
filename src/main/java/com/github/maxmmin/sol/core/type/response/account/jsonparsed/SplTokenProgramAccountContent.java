package com.github.maxmmin.sol.core.type.response.account.jsonparsed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.maxmmin.sol.core.type.response.JsonParsedContent;
import com.github.maxmmin.sol.core.type.response.tx.TokenBalance;

public class SplTokenProgramAccountContent extends JsonParsedContent<SplTokenProgramAccountContent.SqlTokenProgramAccountInfo> {
    public static class SqlTokenProgramAccountInfo {
        @JsonProperty("isNative")
        private Boolean isNative;

        @JsonProperty("mint")
        private String mint;

        @JsonProperty("owner")
        private String owner;

        @JsonProperty("state")
        private String state;

        @JsonProperty("tokenAmount")
        private TokenBalance.UiTokenAmount tokenAmount;
    }
}
