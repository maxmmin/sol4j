package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.response.block.HighestSnapshotSlot;

import java.util.List;

public class GetHighestSnapshotSlotRequest extends SimpleRequest<HighestSnapshotSlot> {
    public GetHighestSnapshotSlotRequest(RpcGateway rpcGateway) {
        super(new TypeReference<HighestSnapshotSlot>() {}, rpcGateway, "getHighestSnapshotSlot", List.of());
    }

    @Override
    public HighestSnapshotSlot send() throws RpcException {
        return super.send();
    }
}
