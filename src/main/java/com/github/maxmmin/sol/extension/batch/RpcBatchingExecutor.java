package com.github.maxmmin.sol.extension.batch;

import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.RpcRequest;

import java.util.List;

public interface RpcBatchingExecutor {
    <V> void execute(List<RpcRequest> requests, int batchSize, RpcRequestsHandler handler) throws RpcException;

    interface RpcRequestsHandler {
        void accept(List<RpcRequest> requests) throws RpcException;
    }
}
