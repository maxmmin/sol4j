package com.github.maxmmin.sol.extension.rotation;

import lombok.Getter;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.extension.feature.Feature;
import com.github.maxmmin.sol.extension.feature.Featured;

import java.util.*;

public class RpcGatewayContext implements Prioritized, Featured {
    @Getter
    private final RpcGateway rpcGateway;
    private final int priority;
    private final Set<Feature> features;

    public RpcGatewayContext(RpcGateway rpcGateway, Collection<Feature> features, int priority) {
        this.rpcGateway = rpcGateway;
        this.priority = priority;
        this.features = Set.of(features.toArray(Feature[]::new));

        if (this.features.size() < features.size())
            throw new IllegalArgumentException("Repeating features were found");
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean hasFeatures(Collection<Feature> features) {
        return this.features.containsAll(features);
    }

    @Override
    public Set<Feature> getFeatures() {
        return this.features;
    }
}
