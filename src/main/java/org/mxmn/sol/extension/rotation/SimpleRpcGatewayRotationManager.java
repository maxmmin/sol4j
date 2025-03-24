package org.mxmn.sol.extension.rotation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mxmn.sol.core.client.RpcGateway;
import org.mxmn.sol.extension.feature.Feature;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SimpleRpcGatewayRotationManager<V extends RpcGateway> implements RpcGatewayRotationManager<V> {
    private final Map<RpcGatewayContext<V>, RotationData> services;
    private final Lock lock = new ReentrantLock();
    private final Long chargeTime = Duration.ofSeconds(10).toMillis();

    private final boolean balanced;

    private final Predicate<RotationData> isAvailable =
            rotationData -> rotationData.getRotatedAt() == null || System.currentTimeMillis() - rotationData.getRotatedAt() > chargeTime;

    public SimpleRpcGatewayRotationManager(List<RpcGatewayContext<V>> registrations) {
        this(registrations, false);
    }

    public SimpleRpcGatewayRotationManager(List<RpcGatewayContext<V>> registrations, boolean balanced) {
        services = new ConcurrentHashMap<>() {{
            registrations.forEach(web3jService -> put(web3jService, new RotationData()));
        }};
        this.balanced = balanced;
    }

    @Override
    public Optional<RpcGatewayContext<V>> tryRotate(RpcGatewayContext<V> web3jService, Set<Feature> requiredFeatures) {
        lock.lock();
        try {
            markAsRotated(getRotationData(web3jService));
            return obtain(requiredFeatures);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Optional<RpcGatewayContext<V>> tryRotate(RpcGatewayContext<V> web3jService) {
        return tryRotate(web3jService, Collections.emptySet());
    }

    @Override
    public boolean backToPool(RpcGatewayContext<V> web3jService, boolean markAsNew) {
        lock.lock();
        try {
            RotationData rotationData = getRotationData(web3jService);
            return markAsNew ? markAsNew(rotationData) : markAsRotated(rotationData);
        } finally {
            lock.unlock();
        }
    }

    protected Optional<RpcGatewayContext<V>> obtain(Set<Feature> requiredFeatures, boolean includeDistributed) {
        return getAvailableWithCondition(e -> {
            if (!includeDistributed && e.getValue().isDistributed()) return false;
            return requiredFeatures.isEmpty() || e.getKey().hasFeatures(requiredFeatures);
        });
    }

    @Override
    public Optional<RpcGatewayContext<V>> obtain(Set<Feature> features) {
        Optional<RpcGatewayContext<V>>context = obtain(features, !balanced);
        if (balanced && context.isEmpty()) {
            return obtain(features, true);
        } else return context;
    }

    @Override
    public Optional<RpcGatewayContext<V>> obtain() {
        return obtain(Collections.emptySet());
    }

    protected Optional<RpcGatewayContext<V>> getAvailableWithCondition(@Nullable Predicate<Map.Entry<RpcGatewayContext<V>, RotationData>> condition) {
        Predicate<Map.Entry<RpcGatewayContext<V>, RotationData>>filter = e -> isAvailable.test(e.getValue());
        if (condition != null) filter = filter.and(condition);

        Stream<Map.Entry<RpcGatewayContext<V>, RotationData>> servicesStream = services.entrySet()
                .stream()
                .filter(filter);

        Optional<Map.Entry<RpcGatewayContext<V>, RotationData>> primary = getWithHighestPriority(servicesStream);
        primary.ifPresent(entry -> markAsDistributed(entry.getValue()));

        return primary.map(Map.Entry::getKey);
    }

    protected Optional<Map.Entry<RpcGatewayContext<V>, RotationData>> getWithHighestPriority(Stream<Map.Entry<RpcGatewayContext<V>, RotationData>> stream) {
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

    protected RotationData getRotationData(RpcGatewayContext<V> web3jService) {
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
