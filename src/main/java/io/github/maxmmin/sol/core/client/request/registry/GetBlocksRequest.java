package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetBlocksConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GetBlocksRequest extends SimpleRequest<List<BigInteger>> {
    public GetBlocksRequest(RpcGateway rpcGateway, BigInteger startBlock, @Nullable BigInteger endBlock, @Nullable GetBlocksConfig config) {
        super(new TypeReference<List<BigInteger>>(){}, rpcGateway, "getBlocks", getParamsList(startBlock, endBlock, config));
    }

    private static List<Object> getParamsList(BigInteger startBlock, BigInteger endBlock, GetBlocksConfig config) {
        List<Object> params = new ArrayList<>(3);
        params.add(startBlock);
        if (endBlock != null) params.add(endBlock);
        if (config != null) params.add(config);
        return params;
    }

    @Override
    public List<BigInteger> send() throws RpcException {
        return super.send();
    }
}
