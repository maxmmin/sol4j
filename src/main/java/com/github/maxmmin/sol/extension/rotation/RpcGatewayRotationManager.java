package com.github.maxmmin.sol.extension.rotation;

import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.extension.feature.Feature;

import java.util.Optional;
import java.util.Set;

public interface RpcGatewayRotationManager<V extends RpcGateway> {
    Optional<RpcGatewayContext<V>> tryRotate(RpcGatewayContext<V> web3jService, Set<Feature> requiredFeatures);
    Optional<RpcGatewayContext<V>> tryRotate(RpcGatewayContext<V> web3jService);
    Optional<RpcGatewayContext<V>> obtain(Set<Feature> features);
    Optional<RpcGatewayContext<V>> obtain();
    boolean backToPool(RpcGatewayContext<V> web3jService, boolean markAsNew);
}


