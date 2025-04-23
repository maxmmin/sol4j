package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.core.type.request.GetSignaturesForAddressConfig;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.response.signature.SignatureInformation;

import java.util.List;
import java.util.Map;

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
