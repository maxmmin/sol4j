package io.github.maxmmin.sol.core.client.exception;

public class WrappedRpcException extends RuntimeException {

    public WrappedRpcException(RpcException rpcException) {
        super(rpcException);
    }

    @Override
    public RpcException getCause() {
        return (RpcException) super.getCause();
    }
}
