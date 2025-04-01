package com.github.maxmmin.sol.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.maxmmin.sol.core.client.request.BatchedRequest;
import com.github.maxmmin.sol.core.client.request.Request;
import com.github.maxmmin.sol.core.client.request.SimpleBatchedRequest;
import com.github.maxmmin.sol.core.client.request.SimpleRequest;
import com.github.maxmmin.sol.core.client.request.registry.*;
import lombok.RequiredArgsConstructor;
import com.github.maxmmin.sol.core.type.request.*;
import com.github.maxmmin.sol.core.type.response.RpcResponse;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
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
    public GetBalanceRequest getBalance(String publicKey) {
        return getBalance(publicKey, GetBalanceConfig.empty());
    }

    @Override
    public GetBalanceRequest getBalance(String publicKey, @NotNull GetBalanceConfig config) {
        return new GetBalanceRequest(rpcGateway, publicKey, config);
    }

    @Override
    public GetBlockCommitmentRequest getBlockCommitment(BigInteger blockNumber) {
        return new GetBlockCommitmentRequest(rpcGateway, blockNumber);
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
    public GetBlockTimeRequest getBlockTime(BigInteger blockNumber) {
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
    public GetFeeForMessageRequest getFeeForMessage(String base64EncodedMessage) {
        return getFeeForMessage(base64EncodedMessage, GetFeeForMessageConfig.empty());
    }

    @Override
    public GetFeeForMessageRequest getFeeForMessage(String base64EncodedMessage, GetFeeForMessageConfig config) {
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
    public MinimumLedgerSlotRequest minimumLedgerSlot() {
        return new MinimumLedgerSlotRequest(rpcGateway);
    }


}
