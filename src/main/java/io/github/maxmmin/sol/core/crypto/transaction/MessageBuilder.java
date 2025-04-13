package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class MessageBuilder {
    @Getter
    @Setter
    private String blockHash;
    private final List<TransactionInstruction> transactionInstructions;

    public MessageBuilder(TransactionInstruction... transactionInstructions) {
        this.transactionInstructions = Arrays.asList(transactionInstructions);
    }

    public MessageBuilder(String blockHash, TransactionInstruction... transactionInstructions) {
        this.blockHash = blockHash;
        this.transactionInstructions = Arrays.asList(transactionInstructions);
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
        if (blockHash == null) throw new IllegalArgumentException("Block hash should be specified");
        List<AccountMeta> accounts = getOrderedAccounts();
        MessageHeader messageHeader = buildMessageHeader(accounts);

    }

    protected MessageHeader buildMessageHeader(List<AccountMeta> accounts) {
        int signersRw = 0;
        int signerRo = 0;
        int ro = 0;

        for (AccountMeta accountMeta : accounts) {
            if (accountMeta.isSigner()) {
                if (accountMeta.isWritable()) signersRw++;
                else signerRo++;
            }
            else ro++;
        }

        return new MessageHeader(signersRw, signerRo, ro);
    }

    protected List<AccountMeta> getOrderedAccounts() {
        Map<PublicKey, List<AccountMeta>> accountMap = new HashMap<>();
        transactionInstructions.forEach(transactionInstruction -> {
            List<AccountMeta> instructionAccounts = transactionInstruction.getAccounts();
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