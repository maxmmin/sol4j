package io.github.maxmmin.sol.core.client.exception;

import io.github.maxmmin.sol.core.client.type.response.RpcResponse;

public class RpcException extends Exception {
    public RpcException(String message) {
        super(message);
    }

    public RpcException(RpcResponse.Error error) {
        super(error.getCode()+ " " + error.getMessage());
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
