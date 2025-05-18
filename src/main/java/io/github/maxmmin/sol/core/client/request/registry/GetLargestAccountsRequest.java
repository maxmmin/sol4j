package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.type.request.GetLargestAccountsConfig;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.account.LargestAccount;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetLargestAccountsRequest extends SimpleRequest<ContextWrapper<List<LargestAccount>>> {
    public GetLargestAccountsRequest(RpcGateway rpcGateway, @Nullable GetLargestAccountsConfig config) {
        super(
                new TypeReference<ContextWrapper<List<LargestAccount>>> () {},
                rpcGateway,
                "getLargestAccounts",
                getParams(config)
        );
    }

    @Override
    public ContextWrapper<List<LargestAccount>> send() throws RpcException {
        return super.send();
    }

    private static List<Object> getParams(@Nullable GetLargestAccountsConfig config) {
        return Stream.of(config).collect(Collectors.toUnmodifiableList());
    }
}
