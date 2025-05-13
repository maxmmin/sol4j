package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetSignaturesForAddressConfig;
import io.github.maxmmin.sol.core.client.type.response.signature.SignatureInformation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetSignaturesForAddressRequest extends SimpleRequest<List<SignatureInformation>> {
    public GetSignaturesForAddressRequest(RpcGateway gateway, String address, @Nullable GetSignaturesForAddressConfig config) {
        super(new TypeReference<List<SignatureInformation>>() {}, gateway, "getSignaturesForAddress", getParams(address, config));
    }

    private static List<Object> getParams(String address, @Nullable GetSignaturesForAddressConfig config) {
        return Stream.of(
                Objects.requireNonNull(address, "Address must be specified"),
                config
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<SignatureInformation> send() throws RpcException {
        return super.send();
    }
}
