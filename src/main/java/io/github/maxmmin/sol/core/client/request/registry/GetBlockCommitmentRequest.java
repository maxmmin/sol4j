package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.type.response.block.BlockCommitment;

import java.math.BigInteger;
import java.util.List;

public class GetBlockCommitmentRequest extends SimpleRequest<BlockCommitment> {
    public GetBlockCommitmentRequest(RpcGateway rpcGateway, BigInteger blockNumber) {
        super(new TypeReference<BlockCommitment>() {}, rpcGateway, "getBlockCommitment", List.of(blockNumber));
    }

    @Override
    public BlockCommitment send() throws RpcException {
        return super.send();
    }
}
