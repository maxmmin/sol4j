package org.mxmn.sol.extension.feature;

import org.mxmn.sol.core.type.request.RpcRequest;

import java.util.Set;

public interface FeaturesDeterminer {
    Set<Feature> getRequiredFeatures(RpcRequest request);
}
