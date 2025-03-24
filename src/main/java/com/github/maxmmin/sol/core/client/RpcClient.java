package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.type.request.*;
import com.github.maxmmin.sol.core.type.response.ContextWrapper;
import com.github.maxmmin.sol.core.type.response.SolanaNodeInfo;
import com.github.maxmmin.sol.core.type.response.account.AccountDetails;
import com.github.maxmmin.sol.core.type.response.account.ProgramAccount;
import com.github.maxmmin.sol.core.type.response.account.base.BaseEncAccountDetails;
import com.github.maxmmin.sol.core.type.response.account.json.JsonProgramAccount;
import com.github.maxmmin.sol.core.type.response.signature.SignatureInformation;
import com.github.maxmmin.sol.core.type.response.tx.Transaction;
import com.github.maxmmin.sol.core.type.response.tx.json.JsonTransaction;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface RpcClient {
    <V> V call(String method, List<Object> params, TypeReference<V> typeRef) throws RpcException;
    <V> void callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef, Map<String, V> target) throws RpcException;
    <V> List<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef) throws RpcException;

    List<SignatureInformation> getSignaturesForAddress(String address) throws RpcException;
    List<SignatureInformation> getSignaturesForAddress(String address, @Nullable GetSignaturesForAddressConfig config) throws RpcException;

    List<JsonProgramAccount> getProgramAccounts(String programId) throws RpcException;
    List<JsonProgramAccount> getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config) throws RpcException;
    <V extends ProgramAccount<?>> List<V> getProgramAccounts(String programId, @Nullable GetProgramAccountsConfig config, TypeReference<V> typeRef) throws RpcException;

    ContextWrapper<List<JsonProgramAccount>> getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params) throws RpcException;
    ContextWrapper<List<JsonProgramAccount>> getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @Nullable GetTokenAccountsByOwnerConfig config) throws RpcException;
    <V extends ProgramAccount<?>> ContextWrapper<List<V>> getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params,
                                                                                  @Nullable GetTokenAccountsByOwnerConfig config, TypeReference<V> typeRef) throws RpcException;

    JsonTransaction getTransaction(String signature) throws RpcException;
    JsonTransaction getTransaction(String signature, @Nullable GetTransactionConfig config) throws RpcException;
    <V extends Transaction<?, ?>> V getTransaction(String signature, @Nullable GetTransactionConfig config, TypeReference<V> typeRef) throws RpcException;

    List<JsonTransaction> getTransaction(List<String>signature) throws RpcException;
    List<JsonTransaction> getTransaction(List<String> signature, @Nullable GetTransactionConfig config) throws RpcException;
    <V extends Transaction<?, ?>> List<V> getTransaction(List<String> signature, @Nullable GetTransactionConfig config, TypeReference<V> typeRef) throws RpcException;

    ContextWrapper<List<BaseEncAccountDetails>> getMultipleAccounts(List<String> accounts) throws RpcException;
    ContextWrapper<List<BaseEncAccountDetails>> getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config) throws RpcException;
    <V extends AccountDetails<?>> ContextWrapper<List<V>> getMultipleAccounts(List<String> accounts, @Nullable GetMultipleAccountsConfig config, TypeReference<V> typeRef) throws RpcException;

    List<SolanaNodeInfo> getClusterNodes() throws RpcException;
}