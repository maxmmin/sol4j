package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.response.epoch.EpochSchedule;

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
