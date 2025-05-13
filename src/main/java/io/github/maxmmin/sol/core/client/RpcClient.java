package io.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.BatchedRequest;
import io.github.maxmmin.sol.core.client.request.Request;
import io.github.maxmmin.sol.core.client.request.registry.*;
import io.github.maxmmin.sol.core.client.type.request.*;
import io.github.maxmmin.sol.core.crypto.transaction.Transaction;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionV0;
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

    GetBlockRequest getBlock(BigInteger slotNumber);
    GetBlockRequest getBlock(BigInteger slotNumber, @NotNull GetBlockConfig config);

    GetBlockCommitmentRequest getBlockCommitment(BigInteger blockNumber);

    GetBlocksRequest getBlocks(BigInteger startBlock, @Nullable BigInteger endBlock);
    GetBlocksRequest getBlocks(BigInteger startBlock, @Nullable BigInteger endBlock, @NotNull GetBlocksConfig config);

    GetBlockHeightRequest getBlockHeight();
    GetBlockHeightRequest getBlockHeight(@NotNull GetBlockHeightConfig config);

    GetBlockProductionRequest getBlockProduction();
    GetBlockProductionRequest getBlockProduction(@NotNull GetBlockProductionConfig config);

    GetBlockTimeRequest getBlockTime(BigInteger blockNumber);

    GetFirstAvailableBlockRequest getFirstAvailableBlock();

    GetGenesisHashRequest getGenesisHash();

    GetHealthRequest getHealth();

    GetHighestSnapshotSlotRequest getHighestSnapshotSlot();

    GetIdentityRequest getIdentity();

    GetInflationGovernorRequest getInflationGovernor();
    GetInflationGovernorRequest getInflationGovernor(@NotNull GetInflationGovernorConfig config);

    GetInflationRateRequest getInflationRate();

    GetInflationRewardRequest getInflationReward(List<String> addresses);
    GetInflationRewardRequest getInflationReward(List<String> addresses, @NotNull GetInflationRewardConfig config);

    GetLatestBlockhashRequest getLatestBlockhash();
    GetLatestBlockhashRequest getLatestBlockhash(@NotNull GetLatestBlockhashConfig config);

    GetEpochInfoRequest getEpochInfo();
    GetEpochInfoRequest getEpochInfo(@NotNull GetEpochInfoConfig config);

    GetEpochScheduleRequest getEpochSchedule();

    GetFeeForMessageRequest getFeeForMessage(String base64EncodedMessage);
    GetFeeForMessageRequest getFeeForMessage(String base64EncodedMessage, GetFeeForMessageConfig config);

    GetSignaturesForAddressRequest getSignaturesForAddress(String address);
    GetSignaturesForAddressRequest getSignaturesForAddress(String address, @NotNull GetSignaturesForAddressConfig config);

    GetProgramAccountsRequest getProgramAccounts(String programId);
    GetProgramAccountsRequest getProgramAccounts(String programId, @NotNull GetProgramAccountsConfig config);

    GetTokenAccountBalanceRequest getTokenAccountBalance(String publicKey);
    GetTokenAccountBalanceRequest getTokenAccountBalance(String publicKey, @NotNull GetTokenAccountBalanceConfig config);

    GetTokenAccountsByDelegateRequest getTokenAccountsByDelegate(String delegate, GetTokenAccountsByDelegateParams params);
    GetTokenAccountsByDelegateRequest getTokenAccountsByDelegate(String delegate, GetTokenAccountsByDelegateParams params, @NotNull GetTokenAccountsByDelegateConfig config);

    GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params);
    GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(String owner, GetTokenAccountsByOwnerParams params, @NotNull GetTokenAccountsByOwnerConfig config);

    GetTokenLargestAccountsRequest getTokenLargestAccounts(String publicKey);
    GetTokenLargestAccountsRequest getTokenLargestAccounts(String publicKey, @NotNull GetTokenLargestAccountsConfig config);

    GetStakeMinimumDelegationRequest getStakeMinimumDelegation();
    GetStakeMinimumDelegationRequest getStakeMinimumDelegation(@NotNull GetStakeMinimumDelegationConfig config);

    GetTransactionRequest getTransaction(String signature);
    GetTransactionRequest getTransaction(String signature, @NotNull GetTransactionConfig config);

    GetTransactionCountRequest getTransactionCount();
    GetTransactionCountRequest getTransactionCount(@NotNull GetTransactionCountConfig config);

    GetMaxRetransmitSlotRequest getMaxRetransmitSlot();
    GetMaxShredInsertSlotRequest getMaxShredInsertSlot();

    GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts);
    GetMultipleAccountsRequest getMultipleAccounts(List<String> accounts, @NotNull GetMultipleAccountsConfig config);

    GetClusterNodesRequest getClusterNodes();

    GetVersionRequest getVersion();

    IsBlockhashValidRequest isBlockhashValid(String blockHash);
    IsBlockhashValidRequest isBlockhashValid(String blockHash, @NotNull IsBlockhashValidConfig config);

    SendTransactionRequest sendTransaction(Transaction transaction);
    SendTransactionRequest sendTransaction(Transaction transaction, @NotNull SendTransactionConfig config);
    SendTransactionRequest sendTransaction(TransactionV0 transactionV0);
    SendTransactionRequest sendTransaction(TransactionV0 transactionV0, @NotNull SendTransactionConfig config);

    MinimumLedgerSlotRequest minimumLedgerSlot();

    static RpcClient create(RpcGateway rpcGateway) {
        return new SimpleRpcClient(rpcGateway);
    }
}