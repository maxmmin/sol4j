package io.github.maxmmin.sol.core.client.type.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse<T> {
    @JsonProperty("jsonrpc")
    private String jsonrpc;

    @JsonProperty("result")
    private T result;

    @JsonProperty("error")
    private Error error;

    @JsonProperty("id")
    private String id;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error {
        private Long code;
        private String message;
    }
}
