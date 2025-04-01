package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.response.epoch.EpochSchedule;

import java.util.List;

public class GetEpochScheduleRequest extends SimpleRequest<EpochSchedule> {
    public GetEpochScheduleRequest(RpcGateway rpcGateway) {
        super(new TypeReference<EpochSchedule>() {}, rpcGateway, "getEpochSchedule", List.of());
    }

    @Override
    public EpochSchedule send() throws RpcException {
        return super.send();
    }
}
