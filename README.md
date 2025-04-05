<h1>Sol4j 👾</h1>
Pure java lib for interacting with Solana RPC API


<h2>Requirements</h2>
- Java 11+

<h2>Dependencies</h2>

- OkHttp
- Jackson
- Project Lombok

<h2>OutOfBox supported methods</h2>
<details>
<summary><b>HTTP</b></summary>
Under active development

- getAccountInfo ✅
- getBalance ✅
- getBlock ❌
- getBlockCommitment ✅
- getBlockHeight ✅
- getBlockProduction ✅
- getBlocks ❌
- getBlocksWithLimit ❌
- getBlockTime ✅
- getClusterNodes ✅
- getEpochInfo ✅
- getEpochSchedule ✅
- getFeeForMessage ✅
- getFirstAvailableBlock ✅
- getGenesisHash ✅
- getHealth ✅
- getHighestSnapshotSlot ✅
- getIdentity ✅
- getInflationGovernor ✅
- getInflationRate ✅
- getInflationReward ❌
- getLargestAccounts ❌
- getLatestBlockhash ✅
- getLeaderSchedule ❌
- getMaxRetransmitSlot ✅
- getMaxShredInsertSlot ✅
- getMinimumBalanceForRentExemption ❌
- getMultipleAccounts ✅
- getProgramAccounts ✅
- getRecentPerformanceSamples ❌
- getRecentPrioritizationFees ❌
- getSignaturesForAddress ✅
- getSignatureStatuses ❌
- getSlot ❌
- getSlotLeader ❌
- getSlotLeaders ❌
- getStakeMinimumDelegation ✅
- getSupply ❌
- getTokenAccountBalance ✅
- getTokenAccountsByDelegate ✅
- getTokenAccountsByOwner ✅
- getTokenLargestAccounts ✅
- getTokenSupply ❌
- getTransaction ✅
- getTransactionCount ✅
- getVersion ✅
- getVoteAccounts ❌
- isBlockhashValid ✅
- minimumLedgerSlot ✅
- requestAirdrop ❌
- sendTransaction ❌
- simulateTransaction ❌
</details>
<details>
<summary><b>WebSocket</b></summary>
Not implemented yet
</details>


<h2>Getting started</h2>

<b>Installation</b>

```xml
<dependency>
    <groupId>com.github.maxmmin</groupId>
    <artifactId>sol4j</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

<b>Creating gateway</b>

```
RpcGateway rpcGateway = HttpRpcGateway.create("https://api.mainnet-beta.solana.com");
```

<b>Creating RPC Client</b>

```
RpcClient client = new SimpleRpcClient(rateLimitedRpcGateway);
```

<b>Making requests</b>

```
List<SolanaNodeInfo> nodes = client.getClusterNodes().send();
```

<b>Multiple encodings support for specific methods</b>

```
GetTransactionRequest txRequest = client.getTransaction(txSignature);

var defaultEncodedTx = txRequest.send();
BaseEncTransaction base58EncodedTx = txRequest.base58();
BaseEncTransaction base64EncodedTx = txRequest.base64();
JsonTransaction jsonEncodedTx = txRequest.json();
JsonParsedTransaction jsonParsedEncTx = txRequest.jsonParsed();
```
