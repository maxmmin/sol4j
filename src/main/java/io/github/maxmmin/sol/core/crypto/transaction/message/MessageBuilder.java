package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.core.crypto.transaction.CompiledInstruction;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MessageBuilder<M> {
    private String recentBlockHash;
    private PublicKey feePayer;
    private final List<TransactionInstruction> transactionInstructions = new ArrayList<>();

    public MessageBuilder<M> setBlockHash(String blockHash) {
        recentBlockHash = Objects.requireNonNull(blockHash, "Block hash cannot be null");
        return this;
    }

    public MessageBuilder<M> setFeePayer(PublicKey feePayer) {
        this.feePayer = Objects.requireNonNull(feePayer, "Fee payer cannot be null");
        return this;
    }

    public MessageBuilder<M> addInstruction(TransactionInstruction transactionInstruction) {
        transactionInstructions.add(transactionInstruction);
        return this;
    }

    public MessageBuilder<M> addInstructions(TransactionInstruction... transactionInstructions) {
        this.transactionInstructions.addAll(Arrays.asList(transactionInstructions));
        return this;
    }

    protected Map<PublicKey, Byte> buildAccountsIndexesMap(List<AccountMeta> accounts) {
        Map<PublicKey, Byte> accountsIndexes = new HashMap<>(accounts.size());
        for (byte i = 0; i < accounts.size(); i++) {
            if (i < 0) throw new RuntimeException("Counter overflow");
            PublicKey publicKey = accounts.get(i).getPubkey();
            accountsIndexes.put(publicKey, i);
        }
        return accountsIndexes;
    }

    protected abstract M build(MessageComponents messageComponents);

    public M build() {
        if (feePayer == null) throw new IllegalArgumentException("Fee payer cannot be null");
        if (recentBlockHash == null) throw new IllegalArgumentException("Block hash cannot be null");
        List<AccountMeta> accounts = getInstructionsOrderedAccounts();
        MessageHeader messageHeader = buildMessageHeader(accounts);
        List<CompiledInstruction> compiledInstructions = compileInstructions(transactionInstructions, accounts);
        List<PublicKey> accountKeys = accounts.stream().map(AccountMeta::getPubkey).collect(Collectors.toList());

        MessageComponents messageComponents = new MessageComponents(messageHeader, accountKeys, accounts, recentBlockHash, feePayer, compiledInstructions);
        return build(messageComponents);
    }

    protected MessageHeader buildMessageHeader(List<AccountMeta> accounts) {
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

    protected List<AccountMeta> getInstructionsOrderedAccounts() {
        Map<PublicKey, AccountMeta> accountMap = new HashMap<>();

        transactionInstructions.forEach(transactionInstruction -> {
            List<AccountMeta> instructionAccounts = new ArrayList<>(transactionInstruction.getAccounts());
            instructionAccounts.add(new AccountMeta(transactionInstruction.getProgramId(), false, false));
            for (AccountMeta account: instructionAccounts) {
                var pubkey = account.getPubkey();
                if (accountMap.containsKey(pubkey) && accountMetaComparator.compare(accountMap.get(pubkey), account) <= 0) {
                    continue;
                }
                accountMap.put(pubkey, account);
            }
        });

        return Stream.concat(
                    Stream.of(new AccountMeta(feePayer, true, true)),
                    accountMap.values().stream()
                                .sorted(accountMetaComparator)
                )
                .collect(Collectors.toList());
    }

    protected static final Comparator<AccountMeta> accountMetaComparator = (AccountMeta o1, AccountMeta o2) -> {
        if (o1.isSigner() && !o2.isSigner()) return -1;
        else if (!o1.isSigner() && o2.isSigner()) return 1;
        else {
            if (o1.isWritable() && !o2.isWritable()) return -1;
            else if (!o1.isWritable() && o2.isWritable()) return 1;
        }

        return 0;
    };

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

    protected byte[] getInstructionAccountIndexes(TransactionInstruction instruction, Map<PublicKey, Byte> indexMap) {
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

    @Getter
    @RequiredArgsConstructor
    protected static class MessageComponents {
        private final MessageHeader messageHeader;
        private final List<PublicKey> accountKeys;
        private final List<AccountMeta> accountMetas;
        private final String recentBlockhash;
        private final PublicKey feePayer;
        private final List<CompiledInstruction> compiledInstructions;
    }

    public static LegacyMessageBuilder getBuilder() {
        return new LegacyMessageBuilder();
    }
}
