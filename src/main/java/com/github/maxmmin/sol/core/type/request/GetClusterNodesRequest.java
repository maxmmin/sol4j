package com.github.maxmmin.sol.core.type.request;

import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.type.response.SolanaNodeInfo;

import java.util.List;

public class GetClusterNodesRequest extends ExecRequest<List<SolanaNodeInfo>, Void, Void, Void> {
    public GetClusterNodesRequest(RpcGateway gateway) {
        super(gateway);
    }

    @Override
    protected RpcRequest constructRequest(Encoding encoding) {
        return new RpcRequest("getClusterNodes", List.of());
    }
}
