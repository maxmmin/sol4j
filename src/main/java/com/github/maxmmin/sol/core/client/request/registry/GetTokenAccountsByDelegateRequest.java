package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetTokenAccountsByDelegateConfig;
import com.github.maxmmin.sol.core.type.request.GetTokenAccountsByDelegateParams;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedProgramAccount;

import java.util.List;
import java.util.Map;

public class GetTokenAccountsByDelegateRequest extends MultiEncRequest<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>,
        ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>> {
    private final String delegate;
    private final GetTokenAccountsByDelegateConfig config;
    private final GetTokenAccountsByDelegateParams params;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetTokenAccountsByDelegateRequest(RpcGateway gateway, String delegate, GetTokenAccountsByDelegateConfig config, GetTokenAccountsByDelegateParams params) {
        super(new RpcTypes<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>, ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>>() {}, gateway);
        this.delegate = delegate;
        this.config = config;
        this.params = params;
    }


    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> paramsMap = objectMapper.convertValue(params, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> cfgMap = objectMapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        if (!encoding.isNil()) cfgMap.put("encoding", encoding);
        return new RpcRequest("getTokenAccountsByDelegate", List.of(delegate, paramsMap, cfgMap));
    }

    @Override
    public ContextWrapper<List<JsonProgramAccount>> send() throws RpcException {
        return super.send();
    }

    @Override
    public ContextWrapper<List<BaseEncProgramAccount>> base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public ContextWrapper<List<BaseEncProgramAccount>> base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public ContextWrapper<List<JsonProgramAccount>> json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public ContextWrapper<List<JsonParsedProgramAccount>> jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
