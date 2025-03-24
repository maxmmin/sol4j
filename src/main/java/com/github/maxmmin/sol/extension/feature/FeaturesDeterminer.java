package com.github.maxmmin.sol.extension.feature;

import com.github.maxmmin.sol.core.type.request.RpcRequest;

import java.util.Set;

public interface FeaturesDeterminer {
    Set<Feature> getRequiredFeatures(RpcRequest request);
}
