package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetLeaderScheduleConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetLeaderScheduleRequest extends SimpleRequest<Map<String, byte[]>> {
    public GetLeaderScheduleRequest(RpcGateway rpcGateway, @Nullable BigInteger slot, @Nullable GetLeaderScheduleConfig config) {
        super(new TypeReference<Map<String, byte[]>>() {}, rpcGateway, "getLeaderSchedule", getParams(slot, config));
    }

    private static List<Object> getParams(@Nullable BigInteger slot, @Nullable GetLeaderScheduleConfig config) {
        return Stream.of(slot, config).collect(Collectors.toUnmodifiableList());
    }
}
