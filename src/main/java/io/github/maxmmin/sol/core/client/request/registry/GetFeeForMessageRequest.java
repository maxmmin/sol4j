package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetFeeForMessageConfig;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;

public class GetFeeForMessageRequest extends SimpleRequest<BigInteger> {
    public GetFeeForMessageRequest(RpcGateway rpcGateway, String base64EncodedMessage, GetFeeForMessageConfig config) {
        super(new TypeReference<BigInteger>() {}, rpcGateway, "getFeeForMessage", List.of(base64EncodedMessage, config));
    }

    @Override
    public @Nullable BigInteger send() throws RpcException {
        return super.send();
    }
}
