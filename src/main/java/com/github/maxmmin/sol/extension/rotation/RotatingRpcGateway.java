package com.github.maxmmin.sol.extension.rotation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.util.Collector;
import lombok.extern.slf4j.Slf4j;
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
    private final SimpleRpcGatewayRotationManager rotationManager;
    private final SimpleFeaturesDeterminer featuresDeterminer;
    private final ConnectionFailDetector connectionFailDetector;

    public RotatingRpcGateway(SimpleRpcGatewayRotationManager rotationManager,
                              SimpleFeaturesDeterminer featuresDeterminer,
                              ConnectionFailDetector connectionFailDetector) {
        this.rotationManager = rotationManager;
        this.featuresDeterminer = featuresDeterminer;
        this.connectionFailDetector = connectionFailDetector;
    }

    public static RotatingRpcGateway create(List<RpcGatewayContext> clients) {
        return create(clients, true);
    }

    public static RotatingRpcGateway create (List<RpcGatewayContext> clients, boolean balanced) {
        return new RotatingRpcGateway(new SimpleRpcGatewayRotationManager(clients, balanced), new SimpleFeaturesDeterminer(), new SimpleConnectionFailDetector());
    }

    protected RpcGatewayContext getRpcContext() {
        return getRpcContext(Set.of());
    }

    protected RpcGatewayContext getRpcContext(Set<Feature> requiredFeatures) {
        return rotationManager.obtain(requiredFeatures)
                .orElseThrow(()->new RuntimeException("No rpc clients available"));
    }

    protected <V> V invokeHandled(Set<Feature> requiredFeatures, RpcCallback<V> callback) throws RpcException {
        RpcGatewayContext serviceHolder = getRpcContext(requiredFeatures);

        while (true) {
            try {
                return callback.doRequest(serviceHolder.getRpcGateway());
            } catch (RpcException targetException) {
                if (connectionFailDetector.isConnectionFail(targetException)) throw targetException;

                log.debug("An exception was thrown while trying to invoke the handler method. Rotating...", targetException);
                String oldEndpoint = serviceHolder.getRpcGateway().getEndpoint();
                serviceHolder = rotationManager.tryRotate(serviceHolder, requiredFeatures).orElse(null);
                if (serviceHolder == null) {
                    log.warn("No available rpc gateways available, try again later.");
                    throw targetException;
                }
                log.debug("Service was successfully rotated. [{} -> {}]", oldEndpoint, serviceHolder.getRpcGateway().getEndpoint());
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
                    requestsRef.get().stream().filter(request -> !target.containsKey(request.getId())).collect(Collectors.toList())
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
        return getRpcContext().getRpcGateway().getEndpoint();
    }

    protected interface RpcCallback<V> {
        V doRequest(RpcGateway rpcGateway) throws RpcException;
    }
}
