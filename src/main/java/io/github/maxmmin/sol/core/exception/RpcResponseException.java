package io.github.maxmmin.sol.core.exception;

import io.github.maxmmin.sol.core.type.response.RpcResponse;

public class RpcResponseException extends RpcException {
    private final RpcResponse<?>errorResponse;

    public RpcResponseException(RpcResponse<?> errorResponse) {
        super(getError(errorResponse.getError()));
        this.errorResponse = errorResponse;
    }

    public RpcResponse<?> getErrorResponse() {
        return errorResponse;
    }

    private static String getError(RpcResponse.Error error) {
        return error.getCode()+ ": " + error.getMessage();
    }
}
