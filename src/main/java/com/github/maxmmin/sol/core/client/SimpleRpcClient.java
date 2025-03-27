package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.maxmmin.sol.core.client.request.*;
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
    public GetSignaturesForAddressRequest getSignaturesForAddress(String address) {
        return getSignaturesForAddress(address, null);
    }

    @Override
    public GetSignaturesForAddressRequest getSignaturesForAddress(String address, @Nullable GetSignaturesForAddressConfig config) {
        return new GetSignaturesForAddressRequest(rpcGateway, address, config != null ? config : GetSignaturesForAddressConfig.builder().build());
    }

    @Override
    public GetProgramAccountsRequest getProgramAccounts(String programId) {
        return getProgramAccounts(programId, null);
    }

    @Override
    public GetProgramAccountsRequest getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config) {
        return new GetProgramAccountsRequest(rpcGateway, programId, config != null ? config : GetProgramAccountsConfig.builder().build());
    }

    @Override
    public GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params) {
        return getTokenAccountsByOwner(owner, params, null);
    }

    @Override
    public GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @Nullable GetTokenAccountsByOwnerConfig config) {
        return new GetTokenAccountsByOwnerRequest(rpcGateway, owner, params, config != null ? config : GetTokenAccountsByOwnerConfig.builder().build());        
    }

    @Override
    public GetTransactionRequest getTransaction(String signature) {
        return getTransaction(signature, null);
    }

    @Override
    public GetTransactionRequest getTransaction(String signature, @Nullable GetTransactionConfig config) {
        return new GetTransactionRequest(rpcGateway, signature, config != null ? config : GetTransactionConfig.builder().build());
    }
    
    @Override
    public GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts) {
        return getMultipleAccounts(accounts, null);
    }

    @Override
    public GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config) {
        return new GetMultipleAccountsRequest(rpcGateway, accounts, config != null ? config : GetMultipleAccountsConfig.builder().build());
    }
    
    @Override
    public GetClusterNodesRequest getClusterNodes() {
        return new GetClusterNodesRequest(rpcGateway);
    }


}
