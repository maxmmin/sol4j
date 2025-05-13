package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetLatestBlockhashConfig;
import io.github.maxmmin.sol.core.client.type.response.block.LatestBlockhashInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetLatestBlockhashRequest extends SimpleRequest<LatestBlockhashInfo> {
    public GetLatestBlockhashRequest(RpcGateway rpcGateway, @Nullable GetLatestBlockhashConfig config) {
        super(new TypeReference<LatestBlockhashInfo>() {}, rpcGateway, "getLatestBlockhash", getParams(config));
    }

    private static List<Object> getParams(@Nullable GetLatestBlockhashConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public LatestBlockhashInfo send() throws RpcException {
        return super.send();
    }
}
