<h1>Sol4j 👾</h1>

[![License: MIT](https://img.shields.io/badge/License-MIT-blue)](https://github.com/maxmmin/sol4j/blob/dev/LICENSE)
![Java required version: 11](https://img.shields.io/badge/Java-11+-yellow)
[![Solana Badge](https://img.shields.io/badge/Solana-%23000000?logo=solana&logoColor=white)](https://solana.com/docs/rpc)

Pure java lib for interacting with Solana RPC API

The library is under active development. I'm currently working on building and sending transactions.

<h2>Requirements</h2>
- Java 11+

<h2>Dependencies</h2>

- OkHttp
- Jackson
- Project Lombok

<h2>OutOfBox supported methods</h2>
<details>
<summary><b>HTTP</b></summary>
<br>
Under active development
<br><br>

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
- getInflationReward ✅
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
<br>
Not implemented yet
</details>


<h2>Getting started</h2>

<b>Installation</b>

```xml
<dependency>
    <groupId>io.github.maxmmin</groupId>
    <artifactId>sol4j</artifactId>
    <version>1.1</version>
</dependency>
```

<b>Creating gateway</b>

```java
RpcGateway rpcGateway = HttpRpcGateway.create("https://api.mainnet-beta.solana.com");
```

<b>Creating RPC Client</b>

```java
RpcClient client = new SimpleRpcClient(rpcGateway);
```

<b>Making requests</b>

```java
List<SolanaNodeInfo> nodes = client.getClusterNodes().send();
```

<b>Multiple encodings support for specific methods</b>

```java
GetTransactionRequest txRequest = client.getTransaction(txSignature);

var defaultEncodedTx = txRequest.send();
BaseEncTransaction base58EncodedTx = txRequest.base58();
BaseEncTransaction base64EncodedTx = txRequest.base64();
JsonTransaction jsonEncodedTx = txRequest.json();
JsonParsedTransaction jsonParsedEncTx = txRequest.jsonParsed();
```
