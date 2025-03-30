package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.request.BatchedRequest;
import com.github.maxmmin.sol.core.client.request.Request;
import com.github.maxmmin.sol.core.client.request.registry.*;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface RpcClient {
    <V> Request<V> call(String method, List<Object> params, TypeReference<V> typeRef) throws RpcException;
    <V> BatchedRequest<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef) throws RpcException;

    GetSignaturesForAddressRequest getSignaturesForAddress(String address);
    GetSignaturesForAddressRequest getSignaturesForAddress(String address, @NotNull GetSignaturesForAddressConfig config);

    GetProgramAccountsRequest getProgramAccounts(String programId);
    GetProgramAccountsRequest getProgramAccounts(String programId, @NotNull GetProgramAccountsConfig config);

    GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params);
    GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @Nullable GetTokenAccountsByOwnerConfig config);

    GetTransactionRequest getTransaction(String signature);
    GetTransactionRequest getTransaction(String signature, @NotNull GetTransactionConfig config);

    GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts);
    GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts, @NotNull GetMultipleAccountsConfig config);

    GetAccountInfoRequest getAccountInfo(String publicKey);
    GetAccountInfoRequest getAccountInfo(String publicKey, @NotNull GetAccountInfoConfig config);

    GetClusterNodesRequest getClusterNodes();
}