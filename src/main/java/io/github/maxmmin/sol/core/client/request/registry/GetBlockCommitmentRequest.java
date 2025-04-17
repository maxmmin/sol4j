package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.response.block.BlockCommitment;

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
