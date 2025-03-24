package org.mxmn.sol.extension.batch;

import org.mxmn.sol.core.exception.RpcException;
import org.mxmn.sol.core.type.request.RpcRequest;

import java.util.List;

public interface RpcBatchingExecutor {
    <V> void execute(List<RpcRequest> requests, int batchSize, RpcRequestsHandler handler) throws RpcException;

    interface RpcRequestsHandler {
        void accept(List<RpcRequest> requests) throws RpcException;
    }
}
