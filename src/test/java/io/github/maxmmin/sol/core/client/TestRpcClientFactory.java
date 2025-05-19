package io.github.maxmmin.sol.core.client;

import io.github.maxmmin.sol.core.client.gateway.HttpRpcGateway;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;

public class TestRpcClientFactory {
    public static RpcClient create() {
        RpcGateway gateway = HttpRpcGateway.create(TestClientProperties.getRpcUrl());
        return RpcClient.create(gateway);
    }
}
