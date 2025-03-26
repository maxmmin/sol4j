package com.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedProgramAccount;

import java.util.List;
import java.util.Map;

public class GetTokenAccountsByOwnerRequest extends ExecRequest<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>,
        ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>> {
    private final String owner;
    private final GetTokenAccountsByOwnerParams params;
    private final GetTokenAccountsByOwnerConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetTokenAccountsByOwnerRequest(RpcGateway gateway, String owner, GetTokenAccountsByOwnerParams params, GetTokenAccountsByOwnerConfig config) {
        super(gateway);
        this.owner = owner;
        this.params = params;
        this.config = config;
    }

    @Override
    protected RpcRequest constructRequest(Encoding encoding) {
        Map<String, Object> paramsMap = objectMapper.convertValue(params, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> cfgMap = objectMapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        if (!encoding.isNil()) cfgMap.put("encoding", encoding);
        return new RpcRequest("getTokenAccountsByOwner", List.of(owner, paramsMap, cfgMap));
    }
}
