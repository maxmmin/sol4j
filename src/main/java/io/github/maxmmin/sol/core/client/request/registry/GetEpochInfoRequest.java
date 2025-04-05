package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.type.request.GetEpochInfoConfig;
import io.github.maxmmin.sol.core.type.response.epoch.EpochInfo;

import java.util.List;

public class GetEpochInfoRequest extends SimpleRequest<EpochInfo> {
    public GetEpochInfoRequest(RpcGateway rpcGateway, GetEpochInfoConfig config) {
        super(new TypeReference<EpochInfo>() {}, rpcGateway, "getEpochInfo", List.of(config));
    }

    @Override
    public EpochInfo send() throws RpcException {
        return super.send();
    }
}
