package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.maxmmin.sol.util.Types;
import lombok.RequiredArgsConstructor;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.*;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.RpcResponse;
import com.github.maxmmin.sol.core.type.response.SolanaNodeInfo;
import com.github.maxmmin.sol.core.type.response.account.Account;
import com.github.maxmmin.sol.core.type.response.account.ProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncAccount;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.signature.SignatureInformation;
import com.github.maxmmin.sol.core.type.response.tx.Transaction;
import com.github.maxmmin.sol.core.type.response.tx.json.JsonTransaction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        List<RpcRequest>requests = paramsLists.stream().map(params -> new RpcRequest(method, params)).collect(Collectors.toList());
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
        List<RpcRequest>requests = params.stream().map(param -> new RpcRequest(method, param)).collect(Collectors.toList());
        return rpcGateway.sendBatched(requests, typeRef).stream()
                .map(this::extractRpcResponse)
                .collect(Collectors.toList());
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
        return call("getProgramAccounts", buildParams(programId, config), Types.toListRef(typeRef));
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
        JavaType responseType = TypeFactory.defaultInstance().constructParametricType(ContextWrapper.class, Types.toListType(typeRef));
        return call("getTokenAccountsByOwner", buildParams(owner, params, config), Types.asRef(responseType));
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
        List<List<Object>>params = signatures.stream().map(signature -> buildParams(signature, config)).collect(Collectors.toList());
        return callBatched("getTransaction", params, typeRef);
    }

    @Override
    public ContextWrapper<List<BaseEncAccount>> getMultipleAccounts(List<String> accounts) throws RpcException {
        return getMultipleAccounts(accounts, null);
    }

    @Override
    public ContextWrapper<List<BaseEncAccount>> getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config) throws RpcException {
        return getMultipleAccounts(accounts, config, new TypeReference<BaseEncAccount>() {});
    }

    @Override
    public <V extends Account<?>> ContextWrapper<List<V>> getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config, TypeReference<V> typeRef) throws RpcException {
        JavaType responseType = TypeFactory.defaultInstance().constructParametricType(ContextWrapper.class, Types.toListType(typeRef));
        return call("getMultipleAccounts", buildParams(accounts, config), Types.asRef(responseType));
    }

    @Override
    public List<SolanaNodeInfo> getClusterNodes() throws RpcException {
        JavaType responseType = TypeFactory.defaultInstance().constructParametricType(List.class, SolanaNodeInfo.class);
        return call("getClusterNodes", null, Types.asRef(responseType));
    }


}
