package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.request.GetAccountInfoConfig;
import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncAccount;
import io.github.maxmmin.sol.core.client.type.response.account.jsonparsed.JsonParsedAccount;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetAccountInfoRequest extends MultiEncRequest<ContextWrapper<BaseEncAccount>, ContextWrapper<BaseEncAccount>, Void, ContextWrapper<JsonParsedAccount>> {
    private final String pubKey;
    private final GetAccountInfoConfig cfg;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetAccountInfoRequest(RpcGateway gateway, String pubKey, @Nullable GetAccountInfoConfig config) {
        super(
                new RpcTypes<ContextWrapper<BaseEncAccount>, ContextWrapper<BaseEncAccount>, Void, ContextWrapper<JsonParsedAccount>>() {},
                new EncodingSupport(Encoding.BASE58, Encoding.BASE64, Encoding.BASE64_ZSTD, Encoding.JSON_PARSED),
                gateway
        );
        this.pubKey = Objects.requireNonNull(pubKey, "Pubkey must not be null");
        this.cfg = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> cfgMap = cfg != null ? objectMapper.convertValue(cfg, new TypeReference<Map<String, Object>>() {}) : new HashMap<>();
        cfgMap.put("encoding", encoding);
        return new RpcRequest("getAccountInfo", List.of(pubKey, cfgMap));
    }

    @Override
    public ContextWrapper<BaseEncAccount> send() throws RpcException {
        return super.send();
    }

    @Override
    public ContextWrapper<BaseEncAccount> base58() throws RpcException, UnsupportedOperationException {
        return super.base58();
    }

    @Override
    public ContextWrapper<BaseEncAccount> base64() throws RpcException, UnsupportedOperationException {
        return super.base64();
    }

    @Override
    public Void json() throws RpcException, UnsupportedOperationException {
        return super.json();
    }

    @Override
    public ContextWrapper<JsonParsedAccount> jsonParsed() throws RpcException, UnsupportedOperationException {
        return super.jsonParsed();
    }
}
