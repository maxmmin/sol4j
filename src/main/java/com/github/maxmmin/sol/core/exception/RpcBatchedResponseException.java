package com.github.maxmmin.sol.core.exception;

import com.github.maxmmin.clue.sol.SolUtil;
import com.github.maxmmin.sol.core.type.response.RpcResponse;

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
