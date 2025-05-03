package io.github.maxmmin.sol.core.client.exception;

import io.github.maxmmin.sol.core.client.type.response.RpcResponse;

import java.util.List;

public class RpcBatchedResponseException extends RpcException {
    public List<RpcResponse<?>> errorResponses;

    public RpcBatchedResponseException(List<RpcResponse<?>> errorResponses) {
        super("Some errors were thrown during batch request. Errors size: " + errorResponses.size());
        this.errorResponses = List.copyOf(errorResponses);
    }

    public List<RpcResponse<?>> getErrorResponses() {
        return errorResponses;
    }
}
