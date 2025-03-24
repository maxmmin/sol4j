package org.mxmn.sol.extension.rotation;

import lombok.RequiredArgsConstructor;
import org.mxmn.sol.core.client.RpcGateway;
import org.mxmn.sol.extension.feature.Feature;
import org.mxmn.sol.extension.RpcConfig;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
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
                .toList();

        return new RpcGatewayContext<>(gateway, features, endpoint.getPriority());
    }
}
