package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetInflationGovernorConfig;
import io.github.maxmmin.sol.core.client.type.response.inflation.InflationGovernor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetInflationGovernorRequest extends SimpleRequest<InflationGovernor> {
    public GetInflationGovernorRequest(RpcGateway rpcGateway, @Nullable GetInflationGovernorConfig config) {
        super(new TypeReference<InflationGovernor>() {}, rpcGateway, "getInflationGovernor", getParams(config));
    }

    private static List<Object> getParams(@Nullable GetInflationGovernorConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public InflationGovernor send() throws RpcException {
        return super.send();
    }
}
