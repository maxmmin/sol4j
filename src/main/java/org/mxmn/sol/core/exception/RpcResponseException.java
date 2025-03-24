package org.mxmn.sol.core.exception;

import org.mxmn.clue.sol.SolUtil;
import org.mxmn.sol.core.type.response.RpcResponse;

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
