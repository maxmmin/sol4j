package com.github.maxmmin.sol.extension.rotation;

import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.extension.feature.Feature;

import java.util.Optional;
import java.util.Set;

public interface RpcGatewayRotationManager<V extends RpcGateway> {
    Optional<RpcGatewayContext> tryRotate(RpcGatewayContext web3jService, Set<Feature> requiredFeatures);
    Optional<RpcGatewayContext> tryRotate(RpcGatewayContext web3jService);
    Optional<RpcGatewayContext> obtain(Set<Feature> features);
    Optional<RpcGatewayContext> obtain();
    boolean backToPool(RpcGatewayContext web3jService, boolean markAsNew);
}


