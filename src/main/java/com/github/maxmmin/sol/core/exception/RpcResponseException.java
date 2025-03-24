package com.github.maxmmin.sol.core.exception;

import com.github.maxmmin.clue.sol.SolUtil;
import com.github.maxmmin.sol.core.type.response.RpcResponse;

public class RpcResponseException extends RpcException {
    private final RpcResponse<?>errorResponse;

    public RpcResponseException(RpcResponse<?> errorResponse) {
        super(SolUtil.getError(errorResponse.getError()));
        this.errorResponse = errorResponse;
    }

    public RpcResponse<?> getErrorResponse() {
        return errorResponse;
    }
}
