package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetProgramAccountsConfig;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedProgramAccount;

import java.util.List;
import java.util.Map;

public class GetProgramAccountsRequest extends MultiEncRequest<List<JsonProgramAccount>, List<BaseEncProgramAccount>, List<JsonProgramAccount>, List<JsonParsedProgramAccount>> {
    private final String programId;
    private final GetProgramAccountsConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetProgramAccountsRequest(RpcGateway gateway, String programId, GetProgramAccountsConfig config) {
        super(new RpcTypes<List<JsonProgramAccount>, List<BaseEncProgramAccount>, List<JsonProgramAccount>, List<JsonParsedProgramAccount>>() {}, gateway);
        this.programId = programId;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, ObjectMapper> cfg = objectMapper.convertValue(config, new TypeReference<Map<String, ObjectMapper>>() {});
        if (!encoding.isNil()) cfg.put("encoding", new ObjectMapper());
        return new RpcRequest("getProgramAccounts", List.of(programId, cfg));
    }

    @Override
    public List<JsonProgramAccount> send() throws RpcException {
        return super.send();
    }

    @Override
    public List<BaseEncProgramAccount> base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public List<BaseEncProgramAccount> base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public List<JsonProgramAccount> json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public List<JsonParsedProgramAccount> jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
