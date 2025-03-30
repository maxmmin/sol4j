package com.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxmmin.sol.core.client.RpcGateway;
import com.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import com.github.maxmmin.sol.core.type.request.Encoding;
import com.github.maxmmin.sol.core.type.request.GetAccountInfoConfig;
import com.github.maxmmin.sol.core.type.request.RpcRequest;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncAccount;
import com.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedAccount;

import java.util.List;
import java.util.Map;

public class GetAccountInfoRequest extends MultiEncRequest<BaseEncAccount, BaseEncAccount, Void, JsonParsedAccount> {
    private final String pubKey;
    private final GetAccountInfoConfig cfg;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetAccountInfoRequest(RpcGateway gateway, String pubKey, GetAccountInfoConfig config) {
        super(gateway);
        this.pubKey = pubKey;
        this.cfg = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = objectMapper.convertValue(cfg, new TypeReference<Map<String, Object>>() {});
        cfgMap.put("encoding", encoding);
        return new RpcRequest("getAccountInfo", List.of(pubKey, cfgMap));
    }
}
