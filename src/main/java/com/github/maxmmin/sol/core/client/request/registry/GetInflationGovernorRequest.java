package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.type.request.GetInflationGovernorConfig;
import com.github.maxmmin.sol.core.type.response.inflation.InflationGovernor;

import java.util.List;

public class GetInflationGovernorRequest extends SimpleRequest<InflationGovernor> {
    public GetInflationGovernorRequest(RpcGateway rpcGateway, GetInflationGovernorConfig config) {
        super(new TypeReference<InflationGovernor>() {}, rpcGateway, "getInflationGovernor", List.of(config));
    }
}
