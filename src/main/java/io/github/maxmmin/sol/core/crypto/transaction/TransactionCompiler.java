package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionCompiler {
    private final List<TransactionInstruction> transactionInstructions;

    public TransactionCompiler(TransactionInstruction... transactionInstructions) {
        this.transactionInstructions = Arrays.asList(transactionInstructions);
    }

    public TransactionCompiler addInstruction(TransactionInstruction transactionInstruction) {
        transactionInstructions.add(transactionInstruction);
        return this;
    }

    public TransactionCompiler addInstructions(TransactionInstruction... transactionInstructions) {
        this.transactionInstructions.addAll(Arrays.asList(transactionInstructions));
        return this;
    }

    public Transaction compile() {

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
    }
}