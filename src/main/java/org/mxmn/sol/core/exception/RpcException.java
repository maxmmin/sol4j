package org.mxmn.sol.core.exception;

import org.mxmn.sol.core.type.response.RpcResponse;

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
