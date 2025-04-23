package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.core.type.request.GetMultipleAccountsConfig;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.type.response.account.base.BaseEncAccount;
import io.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedAccount;

import java.util.List;
import java.util.Map;

public class GetMultipleAccountsRequest extends MultiEncRequest<ContextWrapper<List<BaseEncAccount>>, ContextWrapper<List<BaseEncAccount>>, Void, ContextWrapper<List<JsonParsedAccount>>> {
    private final List<String> accounts;
    private final GetMultipleAccountsConfig config;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetMultipleAccountsRequest(RpcGateway gateway, List<String> accounts, GetMultipleAccountsConfig config) {
        super(
                new RpcTypes<ContextWrapper<List<BaseEncAccount>>, ContextWrapper<List<BaseEncAccount>>, Void, ContextWrapper<List<JsonParsedAccount>>>() {},
                EncodingSupport.fullWithCompressing(),
                gateway);
        this.accounts = accounts;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = mapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
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
