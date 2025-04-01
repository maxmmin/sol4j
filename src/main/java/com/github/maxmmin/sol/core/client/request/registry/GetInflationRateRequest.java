package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.type.response.inflation.InflationRate;

import java.util.List;

public class GetInflationRateRequest extends SimpleRequest<InflationRate> {
    public GetInflationRateRequest(RpcGateway gateway) {
        super(new TypeReference<InflationRate>() {}, gateway, "getInflationRate", List.of());
    }
}
