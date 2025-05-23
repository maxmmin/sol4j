package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.request.GetMultipleAccountsConfig;
import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncAccount;
import io.github.maxmmin.sol.core.client.type.response.account.jsonparsed.JsonParsedAccount;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetMultipleAccountsRequest extends MultiEncRequest<ContextWrapper<List<BaseEncAccount>>, ContextWrapper<List<BaseEncAccount>>, Void, ContextWrapper<List<JsonParsedAccount>>> {
    private final List<String> accounts;
    private final @Nullable GetMultipleAccountsConfig config;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetMultipleAccountsRequest(RpcGateway gateway, List<String> accounts, @Nullable GetMultipleAccountsConfig config) {
        super(
                new RpcTypes<ContextWrapper<List<BaseEncAccount>>, ContextWrapper<List<BaseEncAccount>>, Void, ContextWrapper<List<JsonParsedAccount>>>() {},
                EncodingSupport.fullWithCompressing(),
                gateway
        );
        this.accounts = Objects.requireNonNull(accounts, "Accounts must be specified");
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = config != null ? mapper.convertValue(config, new TypeReference<Map<String, Object>>() {}) : new HashMap<>();
        if (!encoding.isNil()) cfgMap.put("encoding", encoding);
        return new RpcRequest("getMultipleAccounts", List.of(accounts, cfgMap));
    }

    @Override
    public ContextWrapper<List<BaseEncAccount>> send() throws RpcException {
        return super.send();
    }

    @Override
    public ContextWrapper<List<BaseEncAccount>> base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public ContextWrapper<List<BaseEncAccount>> base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public Void json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public ContextWrapper<List<JsonParsedAccount>> jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
