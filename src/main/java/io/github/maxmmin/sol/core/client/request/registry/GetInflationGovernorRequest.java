package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.GetInflationGovernorConfig;
import io.github.maxmmin.sol.core.type.response.inflation.InflationGovernor;

import java.util.List;

public class GetInflationGovernorRequest extends SimpleRequest<InflationGovernor> {
    public GetInflationGovernorRequest(RpcGateway rpcGateway, GetInflationGovernorConfig config) {
        super(new TypeReference<InflationGovernor>() {}, rpcGateway, "getInflationGovernor", List.of(config));
    }

    @Override
    public InflationGovernor send() throws RpcException {
        return super.send();
    }
}
