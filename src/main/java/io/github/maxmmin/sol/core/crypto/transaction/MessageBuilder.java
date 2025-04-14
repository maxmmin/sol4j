package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Account;
import io.github.maxmmin.sol.core.crypto.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.util.ShortU16Util;

import java.util.*;
import java.util.stream.Collectors;

public class MessageBuilder {
    private String recentBlockHash;
    private Account feePayer;
    private final List<TransactionInstruction> transactionInstructions = new ArrayList<>();

    public MessageBuilder setBlockHash(String blockHash) {
        recentBlockHash = Objects.requireNonNull(blockHash, "Block hash cannot be null");
        return this;
    }

    public MessageBuilder setFeePayer(Account feePayer) {
        this.feePayer = Objects.requireNonNull(feePayer, "Fee payer cannot be null");
        return this;
    }

    public MessageBuilder addInstruction(TransactionInstruction transactionInstruction) {
        transactionInstructions.add(transactionInstruction);
        return this;
    }

    public MessageBuilder addInstructions(TransactionInstruction... transactionInstructions) {
        this.transactionInstructions.addAll(Arrays.asList(transactionInstructions));
        return this;
    }

    public Message build() {
        if (feePayer == null) throw new IllegalArgumentException("Fee payer cannot be null");
        if (recentBlockHash == null) throw new IllegalArgumentException("Block hash cannot be null");
        List<AccountMeta> accounts = getOrderedAccounts();
        MessageHeader messageHeader = buildMessageHeader(accounts);
        List<CompiledInstruction> compiledInstructions = compileInstructions(transactionInstructions, accounts);
        List<PublicKey> accountKeys = accounts.stream().map(AccountMeta::getPubkey).collect(Collectors.toList());
        return new Message(messageHeader, accountKeys, recentBlockHash, compiledInstructions, feePayer);
    }

    protected List<CompiledInstruction> compileInstructions(List<TransactionInstruction> instructions, List<AccountMeta> accounts) {
        Map<PublicKey, Byte> accountsIndexes = buildAccountsIndexesMap(accounts);
        return instructions.stream()
                .map(txInstruction -> {
                    Byte programIdIndex = accountsIndexes.get(txInstruction.getProgramId());
                    if (programIdIndex == null) throw new IllegalArgumentException("Program id " + txInstruction.getProgramId() + "index not found");

                    byte[] instructionAccountIndexes = getInstructionAccountIndexes(txInstruction, accountsIndexes);

                    byte[] data = txInstruction.getData();

                    return new CompiledInstruction(programIdIndex, ShortU16Util.serialize(instructionAccountIndexes.length),
                            instructionAccountIndexes, ShortU16Util.serialize(data.length), txInstruction.getData());
                })
                .collect(Collectors.toList());
    }

    private byte[] getInstructionAccountIndexes(TransactionInstruction instruction, Map<PublicKey, Byte> indexMap) {
        List<AccountMeta> instructionAccounts = instruction.getAccounts();
        byte[] indexes = new byte[instructionAccounts.size()];
        for (int i = 0; i < instructionAccounts.size(); i++) {
            AccountMeta account = instructionAccounts.get(i);
            var idx = indexMap.get(account.getPubkey());
            if (idx == null) throw new IllegalArgumentException("Account " + account + "index not found");
            indexes[i] = idx;
        }
        return indexes;
    }

    private Map<PublicKey, Byte> buildAccountsIndexesMap(List<AccountMeta> accounts) {
        Map<PublicKey, Byte> accountsIndexes = new HashMap<>(accounts.size());
        for (byte i = 0; i < accounts.size(); i++) {
            if (i < 0) throw new RuntimeException("Counter overflow");
            PublicKey publicKey = accounts.get(i).getPubkey();
            accountsIndexes.put(publicKey, i);
        }
        return accountsIndexes;
    }

    private MessageHeader buildMessageHeader(List<AccountMeta> accounts) {
        int signers = 0;
        int roSigners = 0;
        int ro = 0;

        for (AccountMeta accountMeta : accounts) {
            if (accountMeta.isSigner()) {
                signers++;
                if (!accountMeta.isWritable()) roSigners++;
            }
            else ro++;
        }

        return new MessageHeader(signers, roSigners, ro);
    }

    private List<AccountMeta> getOrderedAccounts() {
        Map<PublicKey, List<AccountMeta>> accountMap = new HashMap<>();
        List<AccountMeta> feePayerKeys = new ArrayList<>() {{
            add(new AccountMeta(feePayer.getPublicKey(), true, true));
        }};
        accountMap.put(feePayer.getPublicKey(), feePayerKeys);
        transactionInstructions.forEach(transactionInstruction -> {
            List<AccountMeta> instructionAccounts = transactionInstruction.getAccounts();
            instructionAccounts.add(new AccountMeta(transactionInstruction.getProgramId(), false, false));
            for (AccountMeta account : instructionAccounts) {
                var pubkey = account.getPubkey();
                accountMap.putIfAbsent(pubkey, new ArrayList<>(1));
                accountMap.get(pubkey).add(account);
            }
        });
        return accountMap.values().stream()
                .map(accounts -> {
                    accounts.sort(accountMetaComparator);
                    return accounts.get(0);
                })
                .sorted(accountMetaComparator)
                .collect(Collectors.toList());
    }

    private static final Comparator<AccountMeta> accountMetaComparator = (AccountMeta o1, AccountMeta o2) -> {
        if (o1.isSigner() && !o2.isSigner()) return -1;
        else if (!o1.isSigner() && o2.isSigner()) return 1;
        else {
            if (o1.isWritable() && !o2.isWritable()) return -1;
            else if (!o1.isWritable() && o2.isWritable()) return 1;
        }

        return 0;
    };
}