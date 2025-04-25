package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.ShortU16;
import io.github.maxmmin.sol.core.crypto.transaction.CompiledInstruction;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.program.alt.AddressLookupTableAccount;

import java.util.*;
import java.util.stream.Collectors;

public class MessageV0Builder extends MessageBuilder<MessageV0> {
    private final List<AddressLookupTableAccount> lookupTableAccounts = new ArrayList<>();

    @Override
    public MessageBuilder<MessageV0> setBlockHash(String blockHash) {
        return super.setBlockHash(blockHash);
    }

    @Override
    public MessageBuilder<MessageV0> setFeePayer(PublicKey feePayer) {
        return super.setFeePayer(feePayer);
    }

    @Override
    public MessageBuilder<MessageV0> addInstruction(TransactionInstruction transactionInstruction) {
        return super.addInstruction(transactionInstruction);
    }

    @Override
    public MessageBuilder<MessageV0> addInstructions(TransactionInstruction... transactionInstructions) {
        return super.addInstructions(transactionInstructions);
    }

    public MessageV0Builder addLookupTable(AddressLookupTableAccount lookupTable) {
        lookupTableAccounts.add(lookupTable);
        return this;
    }

    public MessageV0Builder addLookupTables(AddressLookupTableAccount... lookupTables) {
        lookupTableAccounts.addAll(Arrays.asList(lookupTables));
        return this;
    }

    @Override
    protected List<AccountMeta> getInstructionsOrderedAccounts() {
        List<AccountMeta> staticAccounts = new ArrayList<>(super.getInstructionsOrderedAccounts());
        Set<PublicKey> replaceableKeys = staticAccounts.stream()
                .filter(meta -> !meta.isSigner())
                .map(AccountMeta::getPubkey)
                .collect(Collectors.toSet());
        for (AddressLookupTableAccount lookupTable: lookupTableAccounts) {
            Set<PublicKey> tableCrossKeys = new HashSet<>(replaceableKeys);
            tableCrossKeys.retainAll(lookupTable.getState().getAddresses());

            List<AccountMeta> tableAccounts = new ArrayList<>(tableCrossKeys.size());
            Iterator<AccountMeta> staticAccountsIterator = staticAccounts.iterator();
            while (tableAccounts.size() < tableCrossKeys.size() && staticAccountsIterator.hasNext()) {
                AccountMeta accountMeta = staticAccountsIterator.next();
                if (tableCrossKeys.contains(accountMeta.getPubkey())) {
                    staticAccountsIterator.remove();
                    tableAccounts.add(accountMeta);
                }
            }

            staticAccounts.addAll(tableAccounts);
            replaceableKeys.removeAll(tableCrossKeys);
        }
        return List.copyOf(staticAccounts);
    }

    @Override
    protected MessageV0 build(MessageComponents messageComponents) {

    }
}
