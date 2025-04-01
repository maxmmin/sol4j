package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.type.request.GetEpochInfoConfig;
import com.github.maxmmin.sol.core.type.response.epoch.EpochInfo;

import java.util.List;

public class GetEpochInfoRequest extends SimpleRequest<EpochInfo> {
    public GetEpochInfoRequest(RpcGateway rpcGateway, GetEpochInfoConfig config) {
        super(new TypeReference<EpochInfo>() {}, rpcGateway, "getEpochInfo", List.of(config));
    }
}
