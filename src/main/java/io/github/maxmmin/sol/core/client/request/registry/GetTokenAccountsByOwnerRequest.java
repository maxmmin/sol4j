package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.exception.RpcException;
import io.github.maxmmin.sol.core.gateway.RpcGateway;
import io.github.maxmmin.sol.core.type.request.Encoding;
import io.github.maxmmin.sol.core.type.request.GetTokenAccountsByOwnerConfig;
import io.github.maxmmin.sol.core.type.request.GetTokenAccountsByOwnerParams;
import io.github.maxmmin.sol.core.type.request.RpcRequest;
import io.github.maxmmin.sol.core.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.type.response.account.base.BaseEncProgramAccount;
import io.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import io.github.maxmmin.sol.core.type.response.account.jsonparsed.JsonParsedProgramAccount;

import java.util.List;
import java.util.Map;

public class GetTokenAccountsByOwnerRequest extends MultiEncRequest<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>,
        ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>> {
    private final String owner;
    private final GetTokenAccountsByOwnerParams params;
    private final GetTokenAccountsByOwnerConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetTokenAccountsByOwnerRequest(RpcGateway gateway, String owner, GetTokenAccountsByOwnerParams params, GetTokenAccountsByOwnerConfig config) {
        super(
                new RpcTypes<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>, ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>>() {},
                new EncodingSupport(Encoding.BASE58, Encoding.BASE64, Encoding.BASE64_ZSTD, Encoding.JSON_PARSED),
                gateway
        );
        this.owner = owner;
        this.params = params;
        this.config = config;
    }

    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> paramsMap = objectMapper.convertValue(params, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> cfgMap = objectMapper.convertValue(config, new TypeReference<Map<String, Object>>() {});
        if (!encoding.isNil()) cfgMap.put("encoding", encoding);
        return new RpcRequest("getTokenAccountsByOwner", List.of(owner, paramsMap, cfgMap));
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
