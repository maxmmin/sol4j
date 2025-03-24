package org.mxmn.sol.extension.feature;

import org.mxmn.sol.core.type.request.RpcRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class SimpleFeaturesDeterminer implements FeaturesDeterminer {
    Map<String, Function<RpcRequest, Set<Feature>>> featuresMap = new HashMap<>() {{
        put("getSignaturesForAddress", ignored -> Set.of(Feature.GET_ADDRESS_SIGNATURES_SUPPORT));
        put("getProgramAccounts", ignored -> Set.of(Feature.GET_PROGRAM_ACCOUNTS_SUPPORT));
        put("getTransaction", ignored -> Set.of(Feature.GET_TRANSACTION_SUPPORT));
    }};

    @Override
    public Set<Feature> getRequiredFeatures(RpcRequest request) {
        String method = request.getMethod();
        return featuresMap.containsKey(method) ? featuresMap.get(method).apply(request) : Set.of();
    }
}
