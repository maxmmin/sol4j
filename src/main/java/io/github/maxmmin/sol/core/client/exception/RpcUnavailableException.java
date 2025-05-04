package io.github.maxmmin.sol.core.client.exception;

import io.github.maxmmin.sol.core.client.type.response.RpcResponse;

public class RpcUnavailableException extends RpcException {
    public RpcUnavailableException(String message) {
        super(message);
    }

    public RpcUnavailableException(RpcResponse.Error error) {
        super(error);
    }

    public RpcUnavailableException(Throwable cause) {
        super(cause);
    }
}
