package io.github.maxmmin.sol.core.client.type.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest {
    @JsonProperty("jsonrpc")
    private String jsonrpc;

    @JsonProperty("method")
    private String method;

    @JsonProperty("params")
    private List<Object> params;

    @JsonProperty("id")
    private String id;

    public RpcRequest(String method) {
        this(method, (List) null);
    }

    public RpcRequest(String method, List<Object> params) {
        this.jsonrpc = "2.0";
        this.id = UUID.randomUUID().toString();
        this.method = method;
        this.params = params == null ? List.of() : params;
    }
}