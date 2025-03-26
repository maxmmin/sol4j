package com.github.maxmmin.sol.core.type.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedProgramAccount;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetProgramAccountsRequest extends ExecRequest<List<JsonProgramAccount>, List<BaseEncProgramAccount>, List<JsonProgramAccount>, List<JsonParsedProgramAccount>> {
    private final String programId;
    private final GetProgramAccountsConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetProgramAccountsRequest(RpcGateway gateway, String programId, GetProgramAccountsConfig config) {
        super(gateway);
        this.programId = programId;
        this.config = config;
    }

    @Override
    protected RpcRequest constructRequest(Encoding encoding) {
        Map<String, ObjectMapper> cfg = objectMapper.convertValue(config, new TypeReference<Map<String, ObjectMapper>>() {});
        if (!encoding.isNil()) cfg.put("encoding", new ObjectMapper());
        return new RpcRequest("getProgramAccounts", List.of(programId, cfg));
    }


}
