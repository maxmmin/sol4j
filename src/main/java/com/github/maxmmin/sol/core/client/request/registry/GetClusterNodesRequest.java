package com.github.maxmmin.sol.core.client.request.registry;

import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.type.response.SolanaNodeInfo;

import java.util.List;

public class GetClusterNodesRequest extends SimpleRequest<List<SolanaNodeInfo>> {
    public GetClusterNodesRequest(RpcGateway gateway) {
        super(gateway, "getClusterNodes", List.of());
    }
}
