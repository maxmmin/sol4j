package com.github.maxmmin.sol.core.client.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetMultipleAccountsConfig;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncAccount;
import com.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedAccount;

import java.util.List;
import java.util.Map;

public class GetMultipleAccountsRequest extends MultiEncRequest<ContextWrapper<List<BaseEncAccount>>, ContextWrapper<List<BaseEncAccount>>, Void, ContextWrapper<List<JsonParsedAccount>>> {
    private final List<String> accounts;
    private final GetMultipleAccountsConfig config;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetMultipleAccountsRequest(RpcGateway gateway, List<String> accounts, GetMultipleAccountsConfig config) {
        super(gateway);
        this.accounts = accounts;
        this.config = config;
    }

    @Override
    protected RpcRequest constructRpcRequest(Encoding encoding) {
        Map<String, Object> cfgMap = mapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        if (!encoding.isNil()) cfgMap.put("encoding", encoding);
        return new RpcRequest("getMultipleAccounts", List.of(accounts, cfgMap));
    }
}
