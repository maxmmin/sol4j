package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.request.BatchedRequest;
import com.github.maxmmin.sol.core.client.request.Request;
import com.github.maxmmin.sol.core.client.request.SimpleBatchedRequest;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.client.request.registry.*;
import lombok.RequiredArgsConstructor;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.*;
import com.github.maxmmin.sol.core.type.response.RpcResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class SimpleRpcClient implements RpcClient {
    private final RpcGateway rpcGateway;

    @Override
    public <V> Request<V> call(String method, List<Object> params, TypeReference<V> typeRef) throws RpcException {
        return new SimpleRequest<>(rpcGateway, method, params);
    }

    @Override
    public <V> BatchedRequest<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef) throws RpcException {
        List<Request<V>>requests = params.stream()
                .map(param -> new SimpleRequest<V>(rpcGateway, method, param))
                .collect(Collectors.toList());

        return new SimpleBatchedRequest<>(rpcGateway, requests);
    }

    protected <V> V extractRpcResponse(RpcResponse<V> rpcResponse) {
       return rpcResponse.getResult();
    }

    @Override
    public GetSignaturesForAddressRequest getSignaturesForAddress(String address) {
        return getSignaturesForAddress(address, GetSignaturesForAddressConfig.empty());
    }

    @Override
    public GetSignaturesForAddressRequest getSignaturesForAddress(String address, @NotNull GetSignaturesForAddressConfig config) {
        return new GetSignaturesForAddressRequest(rpcGateway, address, GetSignaturesForAddressConfig.empty());
    }

    @Override
    public GetProgramAccountsRequest getProgramAccounts(String programId) {
        return getProgramAccounts(programId, GetProgramAccountsConfig.empty());
    }

    @Override
    public GetProgramAccountsRequest getProgramAccounts(String programId, @NotNull GetProgramAccountsConfig config) {
        return new GetProgramAccountsRequest(rpcGateway, programId, config);
    }

    @Override
    public GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params) {
        return getTokenAccountsByOwner(owner, params, GetTokenAccountsByOwnerConfig.empty());
    }

    @Override
    public GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @NotNull GetTokenAccountsByOwnerConfig config) {
        return new GetTokenAccountsByOwnerRequest(rpcGateway, owner, params, config);
    }

    @Override
    public GetTransactionRequest getTransaction(String signature) {
        return getTransaction(signature, GetTransactionConfig.empty());
    }

    @Override
    public GetTransactionRequest getTransaction(String signature, @NotNull GetTransactionConfig config) {
        return new GetTransactionRequest(rpcGateway, signature, config);
    }
    
    @Override
    public GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts) {
        return getMultipleAccounts(accounts, GetMultipleAccountsConfig.empty());
    }

    @Override
    public GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts, @NotNull GetMultipleAccountsConfig config) {
        return new GetMultipleAccountsRequest(rpcGateway, accounts, config);
    }

    @Override
    public GetAccountInfoRequest getAccountInfo(String publicKey) {
        return getAccountInfo(publicKey, GetAccountInfoConfig.empty());
    }

    @Override
    public GetAccountInfoRequest getAccountInfo(String publicKey, @NotNull GetAccountInfoConfig config) {
        return new GetAccountInfoRequest(rpcGateway, publicKey, config);
    }

    @Override
    public GetClusterNodesRequest getClusterNodes() {
        return new GetClusterNodesRequest(rpcGateway);
    }


}
