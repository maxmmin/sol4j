package org.mxmn.sol.core.exception;

import org.mxmn.clue.sol.SolUtil;
import org.mxmn.sol.core.type.response.RpcResponse;

import java.util.List;

public class RpcBatchedResponseException extends RpcException {
    public List<RpcResponse<?>> errorResponses;

    public RpcBatchedResponseException(List<RpcResponse<?>> errorResponses) {
        super("Some errors were thrown during batch request. First is : " + SolUtil.getError(errorResponses.get(0).getError()));
        this.errorResponses = List.copyOf(errorResponses);
    }

    public List<RpcResponse<?>> getErrorResponses() {
        return errorResponses;
    }
}
