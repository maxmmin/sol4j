package io.github.maxmmin.sol.core.client.request.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxmmin.sol.core.client.exception.RpcException;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.enc.MultiEncRequest;
import io.github.maxmmin.sol.core.client.type.request.Encoding;
import io.github.maxmmin.sol.core.client.type.request.GetTokenAccountsByDelegateConfig;
import io.github.maxmmin.sol.core.client.type.request.GetTokenAccountsByDelegateParams;
import io.github.maxmmin.sol.core.client.type.request.RpcRequest;
import io.github.maxmmin.sol.core.client.type.response.ContextWrapper;
import io.github.maxmmin.sol.core.client.type.response.account.base.BaseEncProgramAccount;
import io.github.maxmmin.sol.core.client.type.response.account.json.JsonProgramAccount;
import io.github.maxmmin.sol.core.client.type.response.account.jsonparsed.JsonParsedProgramAccount;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetTokenAccountsByDelegateRequest extends MultiEncRequest<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>,
        ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>> {
    private final String delegate;
    private final GetTokenAccountsByDelegateParams params;
    private final @Nullable GetTokenAccountsByDelegateConfig config;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetTokenAccountsByDelegateRequest(RpcGateway gateway, String delegate, GetTokenAccountsByDelegateParams params, GetTokenAccountsByDelegateConfig config) {
        super(
                new RpcTypes<ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<BaseEncProgramAccount>>, ContextWrapper<List<JsonProgramAccount>>, ContextWrapper<List<JsonParsedProgramAccount>>>() {},
                new EncodingSupport(Encoding.BASE58, Encoding.BASE64, Encoding.BASE64_ZSTD, Encoding.JSON_PARSED),
                gateway
        );
        this.delegate = Objects.requireNonNull(delegate, "Delegate must be specified");
        this.params = Objects.requireNonNull(params, "Params must be specified");
        this.config = config;
    }


    @Override
    protected RpcRequest construct(Encoding encoding) {
        Map<String, Object> paramsMap = objectMapper.convertValue(params, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> cfgMap = config != null ? objectMapper.convertValue(config, new TypeReference<Map<String, Object>>() {}) : new HashMap<>();
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
