package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetEpochInfoConfig;
import io.github.maxmmin.sol.core.client.type.response.epoch.EpochInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetEpochInfoRequest extends SimpleRequest<EpochInfo> {
    public GetEpochInfoRequest(RpcGateway rpcGateway, @Nullable GetEpochInfoConfig config) {
        super(new TypeReference<EpochInfo>() {}, rpcGateway, "getEpochInfo", getParams(config));
    }

    private static List<Object> getParams(@Nullable GetEpochInfoConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EpochInfo send() throws RpcException {
        return super.send();
    }
}
