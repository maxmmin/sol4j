package com.github.maxmmin.sol.core.exception;

import com.github.maxmmin.sol.core.type.response.RpcResponse;

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
