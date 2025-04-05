package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.response.inflation.InflationRate;

import java.util.List;

public class GetInflationRateRequest extends SimpleRequest<InflationRate> {
    public GetInflationRateRequest(RpcGateway gateway) {
        super(new TypeReference<InflationRate>() {}, gateway, "getInflationRate", List.of());
    }

    @Override
    public InflationRate send() throws RpcException {
        return super.send();
    }
}
