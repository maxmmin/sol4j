package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.response.node.SolanaNodeInfo;

import java.util.List;

public class GetClusterNodesRequest extends SimpleRequest<List<SolanaNodeInfo>> {
    public GetClusterNodesRequest(RpcGateway gateway) {
        super(new TypeReference<List<SolanaNodeInfo>>() {}, gateway, "getClusterNodes", List.of());
    }

    @Override
    public List<SolanaNodeInfo> send() throws RpcException {
        return super.send();
    }
}
