package com.github.maxmmin.sol.core.client.request;

import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.SolanaNodeInfo;

import java.util.List;

public class GetClusterNodesRequest extends MultiEncRequest<List<SolanaNodeInfo>, Void, Void, Void> {
    public GetClusterNodesRequest(RpcGateway gateway) {
        super(gateway);
    }

    @Override
    protected RpcRequest constructRpcRequest(Encoding encoding) {
        return new RpcRequest("getClusterNodes", List.of());
    }
}
