package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetSignaturesForAddressConfig;
import io.github.maxmmin.sol.core.client.type.response.signature.SignatureInformation;

import java.util.List;

public class GetSignaturesForAddressRequest extends SimpleRequest<List<SignatureInformation>> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetSignaturesForAddressRequest(RpcGateway gateway, String address, GetSignaturesForAddressConfig config) {
        super(new TypeReference<List<SignatureInformation>>() {}, gateway, "getSignaturesForAddress", List.of(address, config));
    }

    @Override
    public List<SignatureInformation> send() throws RpcException {
        return super.send();
    }
}
