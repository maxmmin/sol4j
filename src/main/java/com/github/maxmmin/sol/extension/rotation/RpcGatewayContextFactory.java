package com.github.maxmmin.sol.extension.rotation;

import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.extension.RpcConfig;
import com.github.maxmmin.sol.extension.feature.Feature;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RpcGatewayContextFactory {
    public <V extends RpcGateway> RpcGatewayContext<V> construct(V gateway, RpcConfig.RpcEndpoint endpoint) {
        List<Feature> features = Arrays.stream(endpoint.getFeatures())
                .map(rpcFeature -> {
                    try {
                        return Feature.valueOf(rpcFeature.getName());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Unknown feature " + rpcFeature.getName());
                    }
                })
                .collect(Collectors.collect(Collectors.toList()));

        return new RpcGatewayContext<>(gateway, features, endpoint.getPriority());
    }
}
