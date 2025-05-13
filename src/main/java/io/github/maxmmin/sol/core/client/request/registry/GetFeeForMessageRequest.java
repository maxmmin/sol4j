package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetFeeForMessageConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetFeeForMessageRequest extends SimpleRequest<BigInteger> {
    public GetFeeForMessageRequest(RpcGateway rpcGateway, String base64EncodedMessage, @Nullable GetFeeForMessageConfig config) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getFeeForMessage", getParams(base64EncodedMessage, config));
    }

    private static List<Object> getParams(String base64EncodedMessage, @Nullable GetFeeForMessageConfig config) {
        return Stream.of(
                        Objects.requireNonNull(base64EncodedMessage, "You must specify the message"),
                        config
                )
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public @Nullable BigInteger send() throws RpcException {
        return super.send();
    }
}
