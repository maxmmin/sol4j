package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.request.BatchedRequest;
import com.github.maxmmin.sol.core.client.request.Request;
import com.github.maxmmin.sol.core.client.request.registry.*;
import com.github.maxmmin.sol.core.type.request.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;

public interface RpcClient {
    <V> Request<V> call(String method, List<Object> params, TypeReference<V> typeRef);
    <V> BatchedRequest<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef);

    GetAccountInfoRequest getAccountInfo(String publicKey);
    GetAccountInfoRequest getAccountInfo(String publicKey, @NotNull GetAccountInfoConfig config);

    GetBalanceRequest getBalance(String publicKey);
    GetBalanceRequest getBalance(String publicKey, @NotNull GetBalanceConfig config);

    GetBlockCommitmentRequest getBlockCommitment(BigInteger blockNumber);

    GetBlockHeightRequest getBlockHeight();
    GetBlockHeightRequest getBlockHeight(@NotNull GetBlockHeightConfig config);

    GetBlockProductionRequest getBlockProduction();
    GetBlockProductionRequest getBlockProduction(@NotNull GetBlockProductionConfig config);

    GetBlockTimeRequest getBlockTime(BigInteger blockNumber);

    GetFirstAvailableBlockRequest getFirstAvailableBlock();

    GetGenesisHashRequest getGenesisHash();

    GetHealthRequest getHealth();

    GetHighestSnapshotSlotRequest getHighestSnapshotSlot();

    GetEpochInfoRequest getEpochInfo();
    GetEpochInfoRequest getEpochInfo(@NotNull GetEpochInfoConfig config);

    GetEpochScheduleRequest getEpochSchedule();

    GetFeeForMessageRequest getFeeForMessage(String base64EncodedMessage);
    GetFeeForMessageRequest getFeeForMessage(String base64EncodedMessage, GetFeeForMessageConfig config);

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

    GetClusterNodesRequest getClusterNodes();
}