package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.IsBlockhashValidConfig;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IsBlockhashValidRequest extends SimpleRequest<Boolean> {
    public IsBlockhashValidRequest(RpcGateway rpcGateway, String blockHash, IsBlockhashValidConfig config) {
        super(new TypeReference<Boolean>() {}, rpcGateway, "isBlockhashValid", getParams(blockHash, config));
    }

    private static List<Object> getParams(String blockhash, @Nullable IsBlockhashValidConfig config) {
        return Stream.of(
                Objects.requireNonNull(blockhash, "Blockhash must be specified"),
                config
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Boolean send() throws RpcException {
        return super.send();
    }
}
