package com.github.maxmmin.sol.core.type.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RpcResponse<T> {
    @JsonProperty("jsonrpc")
    private String jsonrpc;

    @JsonProperty("result")
    private T result;

    @JsonProperty("error")
    private Error error;

    @JsonProperty("id")
    private String id;

    @Data
    public static class Error {
        private Long code;
        private String message;
    }
}
