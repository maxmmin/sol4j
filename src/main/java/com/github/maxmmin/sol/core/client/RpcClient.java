package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.request.*;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.*;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.SolanaNodeInfo;
import com.github.maxmmin.sol.core.type.response.account.Account;
import com.github.maxmmin.sol.core.type.response.account.ProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncAccount;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.signature.SignatureInformation;
import com.github.maxmmin.sol.core.type.response.tx.Transaction;
import com.github.maxmmin.sol.core.type.response.tx.json.JsonTransaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface RpcClient {
    <V> V call(String method, List<Object> params, TypeReference<V> typeRef) throws RpcException;
    <V> void callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef, Map<String, V> target) throws RpcException;
    <V> List<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef) throws RpcException;

    GetSignaturesForAddressRequest getSignaturesForAddress(String address) throws RpcException;
    GetSignaturesForAddressRequest getSignaturesForAddress(String address, @Nullable GetSignaturesForAddressConfig config) throws RpcException;

    GetProgramAccountsRequest getProgramAccounts(String programId) throws RpcException;
    GetProgramAccountsRequest getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config) throws RpcException;
    <V extends ProgramAccount<?>> List<V> getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config, TypeReference<V> typeRef) throws RpcException;

    GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params) throws RpcException;
    GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @Nullable GetTokenAccountsByOwnerConfig config) throws RpcException;

    GetTransactionRequest getTransaction(String signature) throws RpcException;
    GetTransactionRequest getTransaction(String signature, @Nullable GetTransactionConfig config) throws RpcException;

    GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts) throws RpcException;
    GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config) throws RpcException;

    GetClusterNodesRequest getClusterNodes() throws RpcException;
}