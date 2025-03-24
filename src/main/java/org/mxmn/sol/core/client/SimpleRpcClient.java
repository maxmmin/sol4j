package org.mxmn.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.mxmn.clue.core.util.TypesUtil;
import org.mxmn.sol.core.exception.RpcException;
import org.mxmn.sol.core.type.request.*;
import org.mxmn.sol.core.type.response.ContextWrapper;
import org.mxmn.sol.core.type.response.RpcResponse;
import org.mxmn.sol.core.type.response.SolanaNodeInfo;
import org.mxmn.sol.core.type.response.account.AccountDetails;
import org.mxmn.sol.core.type.response.account.ProgramAccount;
import org.mxmn.sol.core.type.response.account.base.BaseEncAccountDetails;
import org.mxmn.sol.core.type.response.account.json.JsonProgramAccount;
import org.mxmn.sol.core.type.response.signature.SignatureInformation;
import org.mxmn.sol.core.type.response.tx.Transaction;
import org.mxmn.sol.core.type.response.tx.json.JsonTransaction;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class SimpleRpcClient implements RpcClient {
    private final RpcGateway rpcGateway;

    protected List<Object> buildParams(Object...params) {
        List<Object>paramsList = new ArrayList<>(params.length);
        for (Object param: params) {
            if (param != null) paramsList.add(param);
        }
        return paramsList;
    }

    @Override
    public <V> V call(String method, List<Object> params, TypeReference<V> typeRef) throws RpcException {
        RpcRequest rpcRequest = new RpcRequest(method, params);
        RpcResponse<V> rpcResponse = rpcGateway.send(rpcRequest, typeRef);
        return extractRpcResponse(rpcResponse);
    }

    @Override
    public <V> void callBatched(String method, List<List<Object>> paramsLists, TypeReference<V> typeRef, Map<String, V> results) throws RpcException {
        Map<String, RpcResponse<V>> intermediateMap = new HashMap<>(results.size());
        List<RpcRequest>requests = paramsLists.stream().map(params -> new RpcRequest(method, params)).toList();
        try {
            rpcGateway.sendBatched(requests, typeRef, intermediateMap);
        } finally {
            for (Map.Entry<String, RpcResponse<V>> entry : intermediateMap.entrySet()) {
                String key = entry.getKey();
                RpcResponse<V> value = entry.getValue();
                results.put(key, extractRpcResponse(value));
            }
        }
    }

    @Override
    public <V> List<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef) throws RpcException {
        List<RpcRequest>requests = params.stream().map(param -> new RpcRequest(method, param)).toList();
        return rpcGateway.sendBatched(requests, typeRef).stream()
                .map(this::extractRpcResponse)
                .toList();
    }

    protected <V> V extractRpcResponse(RpcResponse<V> rpcResponse) {
       return rpcResponse.getResult();
    }

    @Override
    public List<SignatureInformation> getSignaturesForAddress(String address) throws RpcException {
        return getSignaturesForAddress(address, null);
    }

    @Override
    public List<SignatureInformation> getSignaturesForAddress(String address, @Nullable GetSignaturesForAddressConfig config) throws RpcException {
        return call("getSignaturesForAddress", buildParams(address, config), new TypeReference<List<SignatureInformation>>() {});
    }

    @Override
    public List<JsonProgramAccount> getProgramAccounts(String programId) throws RpcException {
        return getProgramAccounts(programId, null);
    }

    @Override
    public List<JsonProgramAccount> getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config) throws RpcException {
        if (config != null && config.getEncoding() != null && config.getEncoding() != Encoding.JSON) throw new IllegalArgumentException("Invalid encoding");
        return getProgramAccounts(programId, config, new TypeReference<JsonProgramAccount>() {});
    }

    @Override
    public <V extends ProgramAccount<?>> List<V> getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config, TypeReference<V> typeRef) throws RpcException {
        return call("getProgramAccounts", buildParams(programId, config), TypesUtil.toListRef(typeRef));
    }

    @Override
    public ContextWrapper<List<JsonProgramAccount>> getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params) throws RpcException {
        return getTokenAccountsByOwner(owner, params, null);
    }

    @Override
    public ContextWrapper<List<JsonProgramAccount>> getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @Nullable GetTokenAccountsByOwnerConfig config) throws RpcException {
        return getTokenAccountsByOwner(owner, params, config, new TypeReference<JsonProgramAccount>() {});
    }

    @Override
    public <V extends ProgramAccount<?>> ContextWrapper<List<V>> getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @Nullable GetTokenAccountsByOwnerConfig config, TypeReference<V> typeRef) throws RpcException {
        JavaType responseType = TypeFactory.defaultInstance().constructParametricType(ContextWrapper.class, TypesUtil.toListType(typeRef));
        return call("getTokenAccountsByOwner", buildParams(owner, params, config), TypesUtil.asRef(responseType));
    }

    @Override
    public JsonTransaction getTransaction(String signature) throws RpcException {
        return getTransaction(signature, null);
    }

    @Override
    public JsonTransaction getTransaction(String signature, @Nullable GetTransactionConfig config) throws RpcException {
        return getTransaction(signature, config, new TypeReference<JsonTransaction>() {});
    }

    @Override
    public <V extends Transaction<?, ?>> V getTransaction(String signature, @Nullable GetTransactionConfig config, TypeReference<V> typeRef) throws RpcException {
        return call("getTransaction", buildParams(signature, config), typeRef);
    }

    @Override
    public List<JsonTransaction> getTransaction(List<String> signatures) throws RpcException {
        return getTransaction(signatures, null);
    }

    @Override
    public List<JsonTransaction> getTransaction(List<String> signatures, @Nullable GetTransactionConfig config) throws RpcException {
        return getTransaction(signatures, config, new TypeReference<JsonTransaction>() {});
    }

    @Override
    public <V extends Transaction<?, ?>> List<V> getTransaction(List<String> signatures, @Nullable GetTransactionConfig config, TypeReference<V> typeRef) throws RpcException {
        List<List<Object>>params = signatures.stream().map(signature -> buildParams(signature, config)).toList();
        return callBatched("getTransaction", params, typeRef);
    }

    @Override
    public ContextWrapper<List<BaseEncAccountDetails>> getMultipleAccounts(List<String> accounts) throws RpcException {
        return getMultipleAccounts(accounts, null);
    }

    @Override
    public ContextWrapper<List<BaseEncAccountDetails>> getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config) throws RpcException {
        return getMultipleAccounts(accounts, config, new TypeReference<BaseEncAccountDetails>() {});
    }

    @Override
    public <V extends AccountDetails<?>> ContextWrapper<List<V>> getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config, TypeReference<V> typeRef) throws RpcException {
        JavaType responseType = TypeFactory.defaultInstance().constructParametricType(ContextWrapper.class, TypesUtil.toListType(typeRef));
        return call("getMultipleAccounts", buildParams(accounts, config), TypesUtil.asRef(responseType));
    }

    @Override
    public List<SolanaNodeInfo> getClusterNodes() throws RpcException {
        JavaType responseType = TypeFactory.defaultInstance().constructParametricType(List.class, SolanaNodeInfo.class);
        return call("getClusterNodes", null, TypesUtil.asRef(responseType));
    }


}
