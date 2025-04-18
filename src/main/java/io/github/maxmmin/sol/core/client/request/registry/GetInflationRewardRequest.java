package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.GetInflationRewardConfig;
import io.github.maxmmin.sol.core.type.response.inflation.InflationReward;

import java.util.List;

public class GetInflationRewardRequest extends SimpleRequest<List<InflationReward>> {
    public GetInflationRewardRequest(RpcGateway rpcGateway, List<String> addresses, GetInflationRewardConfig config) {
        super(new TypeReference<List<InflationReward>>() {}, rpcGateway, "getInflationReward", List.of(addresses, config));
    }

    @Override
    public List<InflationReward> send() throws RpcException {
        return super.send();
    }
}
