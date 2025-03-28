package com.github.maxmmin.sol.extension.rotation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.extension.feature.Feature;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SimpleRpcGatewayRotationManager implements RpcGatewayRotationManager {
    private final Map<RpcGatewayContext, RotationData> services;
    private final Lock lock = new ReentrantLock();
    private final Long chargeTime = Duration.ofSeconds(10).toMillis();

    private final boolean balanced;

    private final Predicate<RotationData> isAvailable =
            rotationData -> rotationData.getRotatedAt() == null || System.currentTimeMillis() - rotationData.getRotatedAt() > chargeTime;

    public SimpleRpcGatewayRotationManager(List<RpcGatewayContext> registrations) {
        this(registrations, false);
    }

    public SimpleRpcGatewayRotationManager(List<RpcGatewayContext> registrations, boolean balanced) {
        services = new ConcurrentHashMap<>() {{
            registrations.forEach(web3jService -> put(web3jService, new RotationData()));
        }};
        this.balanced = balanced;
    }

    @Override
    public Optional<RpcGatewayContext> tryRotate(RpcGatewayContext web3jService, Set<Feature> requiredFeatures) {
        lock.lock();
        try {
            markAsRotated(getRotationData(web3jService));
            return obtain(requiredFeatures);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Optional<RpcGatewayContext> tryRotate(RpcGatewayContext web3jService) {
        return tryRotate(web3jService, Collections.emptySet());
    }

    @Override
    public boolean backToPool(RpcGatewayContext web3jService, boolean markAsNew) {
        lock.lock();
        try {
            RotationData rotationData = getRotationData(web3jService);
            return markAsNew ? markAsNew(rotationData) : markAsRotated(rotationData);
        } finally {
            lock.unlock();
        }
    }

    protected Optional<RpcGatewayContext> obtain(Set<Feature> requiredFeatures, boolean includeDistributed) {
        return getAvailableWithCondition(e -> {
            if (!includeDistributed && e.getValue().isDistributed()) return false;
            return requiredFeatures.isEmpty() || e.getKey().hasFeatures(requiredFeatures);
        });
    }

    @Override
    public Optional<RpcGatewayContext> obtain(Set<Feature> features) {
        Optional<RpcGatewayContext>context = obtain(features, !balanced);
        if (balanced && context.isEmpty()) {
            return obtain(features, true);
        } else return context;
    }

    @Override
    public Optional<RpcGatewayContext> obtain() {
        return obtain(Collections.emptySet());
    }

    protected Optional<RpcGatewayContext> getAvailableWithCondition(@Nullable Predicate<Map.Entry<RpcGatewayContext, RotationData>> condition) {
        Predicate<Map.Entry<RpcGatewayContext, RotationData>>filter = e -> isAvailable.test(e.getValue());
        if (condition != null) filter = filter.and(condition);

        Stream<Map.Entry<RpcGatewayContext, RotationData>> servicesStream = services.entrySet()
                .stream()
                .filter(filter);

        Optional<Map.Entry<RpcGatewayContext, RotationData>> primary = getWithHighestPriority(servicesStream);
        primary.ifPresent(entry -> markAsDistributed(entry.getValue()));

        return primary.map(Map.Entry::getKey);
    }

    protected Optional<Map.Entry<RpcGatewayContext, RotationData>> getWithHighestPriority(Stream<Map.Entry<RpcGatewayContext, RotationData>> stream) {
        return stream.min((e1, e2) ->
                Integer.compare(e2.getKey().getPriority(), e1.getKey().getPriority())
        );
    }

    protected boolean markAsDistributed(RotationData rotationData) {
        if (!rotationData.isDistributed()) {
            rotationData.setDistributed(true);
            return true;
        } else return false;
    }

    protected boolean markAsNew(RotationData rotationData) {
        if (rotationData.isDistributed()) {
            rotationData.setDistributed(false);
            rotationData.setRotatedAt(null);
            return true;
        } else return false;
    }

    protected boolean markAsRotated(RotationData rotationData) {
        if (rotationData.isDistributed()) {
            rotationData.setDistributed(false);
            rotationData.setRotatedAt(System.currentTimeMillis());
            return true;
        } else return false;
    }

    protected RotationData getRotationData(RpcGatewayContext web3jService) {
        RotationData rotationData = services.get(web3jService);
        if (rotationData == null) throw new RuntimeException("Rotation data not found.");
        return rotationData;
    }

    @Getter
    @Setter
    protected static class RotationData {
        private boolean distributed;
        private Long rotatedAt;
    }
}
