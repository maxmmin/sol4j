package org.mxmn.sol.extension.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mxmn.sol.core.exception.RpcBatchedResponseException;
import org.mxmn.sol.core.exception.RpcException;
import org.mxmn.sol.core.client.DelegatingRpcGateway;
import org.mxmn.sol.core.client.RpcGateway;
import org.mxmn.sol.core.exception.RpcResponseException;
import org.mxmn.sol.core.type.request.RpcRequest;
import org.mxmn.sol.core.type.response.RpcResponse;

import java.util.List;
import java.util.Map;

@Slf4j
public class BatchLimitedRpcGateway extends DelegatingRpcGateway {
    @Getter(AccessLevel.PROTECTED)
    private final int batchSize;
    private boolean batchingSupport;
    private final RpcBatchingExecutor batchingExecutor;

    public BatchLimitedRpcGateway(RpcGateway rpcGateway, int batchSize, RpcBatchingExecutor batchingExecutor) {
        super(rpcGateway);
        this.batchSize = batchSize;
        this.batchingExecutor = batchingExecutor;
        this.batchingSupport = batchSize > 0;
    }

    @Override
    public <V> void sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference, Map<String, RpcResponse<V>> target) throws RpcException {
        if (!batchingSupport) {
            batchingExecutor.execute(requests, 1, requestsSlice -> {
                for (RpcRequest request : requests) {
                    try {
                        RpcResponse<V> response = super.send(request, typeReference);
                        target.put(response.getId(), response);
                    } catch (RpcResponseException e) {
                        throw new RpcBatchedResponseException(List.of(e.getErrorResponse()));
                    }
                }
            });
        } else {
            batchingExecutor.execute(requests, batchSize, requestsSlice -> {
                super.sendBatched(requestsSlice, typeReference, target);
            });
        }
    }

}
