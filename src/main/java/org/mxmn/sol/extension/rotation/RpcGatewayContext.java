package org.mxmn.sol.extension.rotation;

import lombok.Getter;
import org.mxmn.sol.core.client.RpcGateway;
import org.mxmn.sol.extension.feature.Feature;
import org.mxmn.sol.extension.feature.Featured;

import java.util.*;

public class RpcGatewayContext<S extends RpcGateway> implements Prioritized, Featured {
    @Getter
    private final S web3jService;
    private final int priority;
    private final Set<Feature> features;

    public RpcGatewayContext(S web3jService, Collection<Feature> features, int priority) {
        this.web3jService = web3jService;
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
