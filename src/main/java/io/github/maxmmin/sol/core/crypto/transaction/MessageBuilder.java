package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;

import java.util.*;
import java.util.stream.Collectors;

public class MessageBuilder {
    private String recentBlockHash;
    private PublicKey feePayer;
    private final List<TransactionInstruction> transactionInstructions = new ArrayList<>();

    public MessageBuilder setBlockHash(String blockHash) {
        recentBlockHash = Objects.requireNonNull(blockHash, "Block hash cannot be null");
        return this;
    }

    public MessageBuilder setFeePayer(PublicKey feePayer) {
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
        return new Message(messageHeader, accountKeys, recentBlockHash, compiledInstructions);
    }

    protected List<CompiledInstruction> compileInstructions(List<TransactionInstruction> instructions, List<AccountMeta> accounts) {
        Map<PublicKey, Byte> accountsIndexes = buildAccountsIndexesMap(accounts);
        return instructions.stream()
                .map(txInstruction -> {
                    Byte programIdIndex = accountsIndexes.get(txInstruction.getProgramId());
                    if (programIdIndex == null) throw new IllegalArgumentException("Program id " + txInstruction.getProgramId() + "index not found");

                    byte[] instructionAccountIndexes = getInstructionAccountIndexes(txInstruction, accountsIndexes);

                    byte[] data = txInstruction.getData();

                    return new CompiledInstruction(programIdIndex, ShortU16.valueOf(instructionAccountIndexes.length),
                            instructionAccountIndexes, ShortU16.valueOf(data.length), txInstruction.getData());
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
        byte roSigners = 0;
        int ro = 0;

        for (AccountMeta accountMeta : accounts) {
            if (accountMeta.isSigner()) {
                signers++;
                if (!accountMeta.isWritable()) roSigners++;
            }
            else if (!accountMeta.isWritable()) ro++;
        }
        int maxValue = Byte.toUnsignedInt((byte) 0xff);
        if (signers > maxValue || ro > maxValue) throw new IllegalArgumentException("Participants overflow. Accounts list is too large");
        return new MessageHeader((byte) signers, roSigners, (byte) ro);
    }

    private List<AccountMeta> getOrderedAccounts() {
        Map<PublicKey, List<AccountMeta>> accountMap = new HashMap<>();
        List<AccountMeta> feePayerKeys = new ArrayList<>() {{
            add(new AccountMeta(feePayer, true, true));
        }};
        accountMap.put(feePayer, feePayerKeys);
        transactionInstructions.forEach(transactionInstruction -> {
            List<AccountMeta> instructionAccounts = new ArrayList<>(transactionInstruction.getAccounts());
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
                .sorted(getComparator())
                .collect(Collectors.toList());
    }

    private Comparator<AccountMeta> getComparator() {
        return (new Comparator<AccountMeta>() {
            @Override
            public int compare(AccountMeta account1, AccountMeta account2) {
                if (account1.getPubkey().equals(feePayer)) return -1;
                else if (account2.getPubkey().equals(feePayer)) return 1;
                else return 0;
            }
        }).thenComparing(accountMetaComparator);
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