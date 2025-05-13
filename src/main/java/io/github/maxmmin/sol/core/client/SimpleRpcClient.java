package io.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.maxmmin.sol.core.client.gateway.RpcGateway;
import io.github.maxmmin.sol.core.client.request.BatchedRequest;
import io.github.maxmmin.sol.core.client.request.Request;
import io.github.maxmmin.sol.core.client.request.SimpleBatchedRequest;
import io.github.maxmmin.sol.core.client.request.SimpleRequest;
import io.github.maxmmin.sol.core.client.request.registry.*;
import io.github.maxmmin.sol.core.client.type.request.*;
import io.github.maxmmin.sol.core.crypto.transaction.Transaction;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionSerializer;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionV0;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class SimpleRpcClient implements RpcClient {
    private final RpcGateway rpcGateway;

    @Override
    public <V> Request<V> call(String method, List<Object> params, TypeReference<V> typeRef) {
        return new SimpleRequest<>(typeRef, rpcGateway, method, params);
    }

    @Override
    public <V> BatchedRequest<V> callBatched(String method, List<List<Object>> params, TypeReference<V> typeRef) {
        List<Request<V>>requests = params.stream()
                .map(param -> new SimpleRequest<V>(typeRef, rpcGateway, method, param))
                .collect(Collectors.toList());

        return new SimpleBatchedRequest<>(typeRef, rpcGateway, requests);
    }

    @Override
    public GetSignaturesForAddressRequest getSignaturesForAddress(@NotNull String address) {
        return getSignaturesForAddress(address, GetSignaturesForAddressConfig.empty());
    }

    @Override
    public GetSignaturesForAddressRequest getSignaturesForAddress(@NotNull String address, @NotNull GetSignaturesForAddressConfig config) {
        return new GetSignaturesForAddressRequest(rpcGateway, address, config);
    }

    @Override
    public GetProgramAccountsRequest getProgramAccounts(@NotNull String programId) {
        return getProgramAccounts(programId, GetProgramAccountsConfig.empty());
    }

    @Override
    public GetProgramAccountsRequest getProgramAccounts(@NotNull String programId, @NotNull GetProgramAccountsConfig config) {
        return new GetProgramAccountsRequest(rpcGateway, programId, config);
    }

    @Override
    public GetTokenAccountBalanceRequest getTokenAccountBalance(@NotNull String publicKey) {
        return getTokenAccountBalance(publicKey, GetTokenAccountBalanceConfig.empty());
    }

    @Override
    public GetTokenAccountBalanceRequest getTokenAccountBalance(@NotNull String publicKey, @NotNull GetTokenAccountBalanceConfig config) {
        return new GetTokenAccountBalanceRequest(rpcGateway, publicKey, config);
    }

    @Override
    public GetTokenAccountsByDelegateRequest getTokenAccountsByDelegate(@NotNull String delegate, GetTokenAccountsByDelegateParams params) {
        return getTokenAccountsByDelegate(delegate, params, GetTokenAccountsByDelegateConfig.empty());
    }

    @Override
    public GetTokenAccountsByDelegateRequest getTokenAccountsByDelegate(@NotNull String delegate, GetTokenAccountsByDelegateParams params, @NotNull GetTokenAccountsByDelegateConfig config) {
        return new GetTokenAccountsByDelegateRequest(rpcGateway, delegate, params, config);
    }

    @Override
    public GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(@NotNull String owner, GetTokenAccountsByOwnerParams params) {
        return getTokenAccountsByOwner(owner, params, GetTokenAccountsByOwnerConfig.empty());
    }

    @Override
    public GetTokenAccountsByOwnerRequest getTokenAccountsByOwner(@NotNull String owner, GetTokenAccountsByOwnerParams params, @NotNull GetTokenAccountsByOwnerConfig config) {
        return new GetTokenAccountsByOwnerRequest(rpcGateway, owner, params, config);
    }

    @Override
    public GetTokenLargestAccountsRequest getTokenLargestAccounts(@NotNull String publicKey) {
        return getTokenLargestAccounts(publicKey, GetTokenLargestAccountsConfig.empty());
    }

    @Override
    public GetTokenLargestAccountsRequest getTokenLargestAccounts(@NotNull String publicKey, @NotNull GetTokenLargestAccountsConfig config) {
        return new GetTokenLargestAccountsRequest(rpcGateway, publicKey, config);
    }

    @Override
    public GetStakeMinimumDelegationRequest getStakeMinimumDelegation() {
        return getStakeMinimumDelegation(GetStakeMinimumDelegationConfig.empty());
    }

    @Override
    public GetStakeMinimumDelegationRequest getStakeMinimumDelegation(@NotNull GetStakeMinimumDelegationConfig config) {
        return new GetStakeMinimumDelegationRequest(rpcGateway, config);
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
    public GetTransactionCountRequest getTransactionCount() {
        return getTransactionCount(GetTransactionCountConfig.empty());
    }

    @Override
    public GetTransactionCountRequest getTransactionCount(@NotNull GetTransactionCountConfig config) {
        return new GetTransactionCountRequest(rpcGateway, config);
    }

    @Override
    public GetMaxRetransmitSlotRequest getMaxRetransmitSlot() {
        return new GetMaxRetransmitSlotRequest(rpcGateway);
    }

    @Override
    public GetMaxShredInsertSlotRequest getMaxShredInsertSlot() {
        return new GetMaxShredInsertSlotRequest(rpcGateway);
    }

    @Override
    public GetMultipleAccountsRequest getMultipleAccounts(@NotNull List<String> accounts) {
        return getMultipleAccounts(accounts, GetMultipleAccountsConfig.empty());
    }

    @Override
    public GetMultipleAccountsRequest getMultipleAccounts(@NotNull List<String> accounts, @NotNull GetMultipleAccountsConfig config) {
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
    public GetBalanceRequest getBalance(String publicKey) {
        return getBalance(publicKey, GetBalanceConfig.empty());
    }

    @Override
    public GetBalanceRequest getBalance(String publicKey, @NotNull GetBalanceConfig config) {
        return new GetBalanceRequest(rpcGateway, publicKey, config);
    }

    @Override
    public GetBlockRequest getBlock(BigInteger slotNumber) {
        return getBlock(slotNumber, GetBlockConfig.empty());
    }

    @Override
    public GetBlockRequest getBlock(BigInteger slotNumber, @NotNull GetBlockConfig config) {
        return new GetBlockRequest(rpcGateway, slotNumber, config);
    }

    @Override
    public GetBlockCommitmentRequest getBlockCommitment(BigInteger blockNumber) {
        return new GetBlockCommitmentRequest(rpcGateway, blockNumber);
    }

    @Override
    public GetBlocksRequest getBlocks(BigInteger startBlock, @Nullable BigInteger endBlock) {
        return getBlocks(startBlock, endBlock, GetBlocksConfig.empty());
    }

    @Override
    public GetBlocksRequest getBlocks(BigInteger startBlock, @Nullable BigInteger endBlock, @NotNull GetBlocksConfig config) {
        return new GetBlocksRequest(rpcGateway, startBlock, endBlock, config);
    }

    @Override
    public GetBlocksWithLimitRequest getBlocksWithLimit(BigInteger startBlock, @Nullable BigInteger limit) {
        return getBlocksWithLimit(startBlock, limit, GetBlocksConfig.empty());
    }

    @Override
    public GetBlocksWithLimitRequest getBlocksWithLimit(BigInteger startBlock, @Nullable BigInteger limit, @NotNull GetBlocksConfig config) {
        return new GetBlocksWithLimitRequest(rpcGateway, startBlock, limit, config);
    }

    @Override
    public GetBlockHeightRequest getBlockHeight() {
        return getBlockHeight(GetBlockHeightConfig.empty());
    }

    @Override
    public GetBlockHeightRequest getBlockHeight(@NotNull GetBlockHeightConfig config) {
        return new GetBlockHeightRequest(rpcGateway, config);
    }

    @Override
    public GetBlockProductionRequest getBlockProduction() {
        return getBlockProduction(GetBlockProductionConfig.empty());
    }

    @Override
    public GetBlockProductionRequest getBlockProduction(@NotNull GetBlockProductionConfig config) {
        return new GetBlockProductionRequest(rpcGateway, config);
    }

    @Override
    public GetBlockTimeRequest getBlockTime(@NotNull BigInteger blockNumber) {
        return new GetBlockTimeRequest(rpcGateway, blockNumber);
    }

    @Override
    public GetFirstAvailableBlockRequest getFirstAvailableBlock() {
        return new GetFirstAvailableBlockRequest(rpcGateway);
    }

    @Override
    public GetGenesisHashRequest getGenesisHash() {
        return new GetGenesisHashRequest(rpcGateway);
    }

    @Override
    public GetHealthRequest getHealth() {
        return new GetHealthRequest(rpcGateway);
    }

    @Override
    public GetHighestSnapshotSlotRequest getHighestSnapshotSlot() {
        return new GetHighestSnapshotSlotRequest(rpcGateway);
    }

    @Override
    public GetIdentityRequest getIdentity() {
        return new GetIdentityRequest(rpcGateway);
    }

    @Override
    public GetInflationGovernorRequest getInflationGovernor() {
        return getInflationGovernor(GetInflationGovernorConfig.empty());
    }

    @Override
    public GetInflationGovernorRequest getInflationGovernor(@NotNull GetInflationGovernorConfig config) {
        return new GetInflationGovernorRequest(rpcGateway, config);
    }

    @Override
    public GetInflationRateRequest getInflationRate() {
        return new GetInflationRateRequest(rpcGateway);
    }

    @Override
    public GetInflationRewardRequest getInflationReward(List<String> addresses) {
        return getInflationReward(addresses, GetInflationRewardConfig.empty());
    }

    @Override
    public GetInflationRewardRequest getInflationReward(List<String> addresses, @NotNull GetInflationRewardConfig config) {
        return new GetInflationRewardRequest(rpcGateway, addresses, config);
    }

    @Override
    public GetLatestBlockhashRequest getLatestBlockhash() {
        return getLatestBlockhash(GetLatestBlockhashConfig.empty());
    }

    @Override
    public GetLatestBlockhashRequest getLatestBlockhash(@NotNull GetLatestBlockhashConfig config) {
        return new GetLatestBlockhashRequest(rpcGateway, config);
    }

    @Override
    public GetEpochInfoRequest getEpochInfo() {
        return getEpochInfo(GetEpochInfoConfig.empty());
    }

    @Override
    public GetEpochInfoRequest getEpochInfo(@NotNull GetEpochInfoConfig config) {
        return new GetEpochInfoRequest(rpcGateway, config);
    }

    @Override
    public GetEpochScheduleRequest getEpochSchedule() {
        return new GetEpochScheduleRequest(rpcGateway);
    }

    @Override
    public GetFeeForMessageRequest getFeeForMessage(@NotNull String base64EncodedMessage) {
        return getFeeForMessage(base64EncodedMessage, GetFeeForMessageConfig.empty());
    }

    @Override
    public GetFeeForMessageRequest getFeeForMessage(@NotNull String base64EncodedMessage, GetFeeForMessageConfig config) {
        return new GetFeeForMessageRequest(rpcGateway, base64EncodedMessage, config);
    }

    @Override
    public GetClusterNodesRequest getClusterNodes() {
        return new GetClusterNodesRequest(rpcGateway);
    }

    @Override
    public GetVersionRequest getVersion() {
        return new GetVersionRequest(rpcGateway);
    }

    @Override
    public IsBlockhashValidRequest isBlockhashValid(String blockHash) {
        return isBlockhashValid(blockHash, IsBlockhashValidConfig.empty());
    }

    @Override
    public IsBlockhashValidRequest isBlockhashValid(String blockHash, @NotNull IsBlockhashValidConfig config) {
        return new IsBlockhashValidRequest(rpcGateway, blockHash, config);
    }

    @Override
    public SendTransactionRequest sendTransaction(Transaction transaction) {
        return sendTransaction(transaction, SendTransactionConfig.empty());
    }

    @Override
    public SendTransactionRequest sendTransaction(Transaction transaction, @NotNull SendTransactionConfig config) {
        byte[] txBytes = TransactionSerializer.getSerializer().serialize(transaction);
        return new SendTransactionRequest(rpcGateway, Base64.getEncoder().encodeToString(txBytes), config);
    }

    @Override
    public SendTransactionRequest sendTransaction(TransactionV0 transactionV0) {
        return sendTransaction(transactionV0, SendTransactionConfig.empty());
    }

    @Override
    public SendTransactionRequest sendTransaction(TransactionV0 transactionV0, @NotNull SendTransactionConfig config) {
        byte[] txBytes = TransactionSerializer.getSerializerV0().serialize(transactionV0);
        return new SendTransactionRequest(rpcGateway, Base64.getEncoder().encodeToString(txBytes), config);
    }

    @Override
    public MinimumLedgerSlotRequest minimumLedgerSlot() {
        return new MinimumLedgerSlotRequest(rpcGateway);
    }


}
