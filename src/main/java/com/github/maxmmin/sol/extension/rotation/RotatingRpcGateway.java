package com.github.maxmmin.sol.extension.rotation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.Collector;
import lombok.extern.slf4j.Slf4j;
import com.github.maxmmin.clue.sol.SolUtil;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.RpcResponse;
import com.github.maxmmin.sol.extension.feature.Feature;
import com.github.maxmmin.sol.extension.feature.SimpleFeaturesDeterminer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class RotatingRpcGateway implements RpcGateway {
    private final SimpleRpcGatewayRotationManager<RpcGateway> rotationManager;
    private final SimpleFeaturesDeterminer featuresDeterminer;

    public RotatingRpcGateway(SimpleRpcGatewayRotationManager<RpcGateway> rotationManager, SimpleFeaturesDeterminer featuresDeterminer) {
        this.rotationManager = rotationManager;
        this.featuresDeterminer = featuresDeterminer;
    }

    public static RotatingRpcGateway create (List<RpcGatewayContext<RpcGateway>> clients) {
        return new RotatingRpcGateway(new SimpleRpcGatewayRotationManager<>(clients, true), new SimpleFeaturesDeterminer());
    }

    protected RpcGatewayContext<RpcGateway>getRpcContext() {
        return getRpcContext(Set.of());
    }

    protected RpcGatewayContext<RpcGateway>getRpcContext(Set<Feature> requiredFeatures) {
        return rotationManager.obtain(requiredFeatures)
                .orElseThrow(()->new RuntimeException("No rpc clients available"));
    }

    protected <V> V invokeHandled(Set<Feature> requiredFeatures, RpcCallback<V> callback) throws RpcException {
        RpcGatewayContext<RpcGateway> serviceHolder = getRpcContext(requiredFeatures);

        while (true) {
            try {
                return callback.doRequest(serviceHolder.getWeb3jService());
            } catch (RpcException targetException) {
                log.debug("An exception was thrown while trying to invoke the handler method. Rotating...", targetException);
                String oldEndpoint = serviceHolder.getWeb3jService().getEndpoint();
                serviceHolder = rotationManager.tryRotate(serviceHolder, requiredFeatures).orElse(null);
                if (serviceHolder == null) {
                    log.warn("No available rpc gateways available, try again later.");
                    throw targetException;
                }
                log.debug("Service was successfully rotated. [{} -> {}]", oldEndpoint, serviceHolder.getWeb3jService().getEndpoint());
            }
        }
    }

    protected Set<Feature> getFeatures(RpcRequest request) {
        return featuresDeterminer.getRequiredFeatures(request);
    }

    @Override
    public <V> RpcResponse<V> send(RpcRequest request, TypeReference<V> typeReference) throws RpcException {
        return invokeHandled(getFeatures(request), rpc -> rpc.send(request, typeReference));
    }

    @Override
    public <V> void sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference, Map<String, RpcResponse<V>> target) throws RpcException {
        Set<Feature> features = requests.stream()
                .map(this::getFeatures)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        AtomicReference<List<RpcRequest>> requestsRef = new AtomicReference<>(requests);
        invokeHandled(features, rpcGateway -> {
            if (!target.isEmpty()) requestsRef.set(
                    requestsRef.get().stream().filter(request -> !target.containsKey(request.getId())).toList()
            );
            rpcGateway.sendBatched(requestsRef.get(), typeReference, target);
            return null;
        });
    }

    @Override
    public <V> List<RpcResponse<V>> sendBatched(List<RpcRequest> requests, TypeReference<V> typeReference) throws RpcException {
        Map<String, RpcResponse<V>> resultMap = new ConcurrentHashMap<>();
        sendBatched(requests, typeReference, resultMap);
        return Collector.collectResultsOrdered(requests, resultMap);
    }

    @Override
    public String getEndpoint() {
        return getRpcContext().getWeb3jService().getEndpoint();
    }

    protected interface RpcCallback<V> {
        V doRequest(RpcGateway rpcGateway) throws RpcException;
    }
}
