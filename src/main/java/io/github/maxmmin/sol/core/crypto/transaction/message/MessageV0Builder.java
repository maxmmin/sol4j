package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
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
        messageComponents.getAccountKeys().subList(messageComponents.getAccountKeys().size() - lookupTableAccounts.size() - 1, lookupTableAccounts.size());
    }

    protected List<MessageAddressTableLookup> getLookupAccounts(List<AccountMeta> allAccounts) {
        Map<PublicKey, AccountMeta> accountMetaMap = allAccounts.stream()
                .filter(meta -> !meta.isSigner())
                .collect(Collectors.toMap(AccountMeta::getPubkey, a -> a));

        List<MessageAddressTableLookup> messageAddressTableLookups = new ArrayList<>();
        for (AddressLookupTableAccount lookupTable: lookupTableAccounts) {
            List<Byte> writableIndexes = new ArrayList<>();
            List<Byte> readonlyIndexes = new ArrayList<>();
            List<PublicKey> tableAddresses = lookupTable.getState().getAddresses();

            for (byte i = 0; i < tableAddresses.size(); i++) {
                if (i < 0) throw new IllegalStateException("Counter overflow: lookup table accounts amount are too large");
                PublicKey pubkey = tableAddresses.get(i);
                if (accountMetaMap.containsKey(pubkey)) {
                    AccountMeta meta = accountMetaMap.get(pubkey);
                    if (meta.isWritable()) writableIndexes.add(i);
                    else readonlyIndexes.add(i);
                    accountMetaMap.remove(pubkey);
                }
            }

            byte[] writableIndexesArray = new byte[writableIndexes.size()];
            for (int i = 0; i < writableIndexes.size(); i++) {
                writableIndexesArray[i] = writableIndexes.get(i);
            }

            byte[] readonlyIndexesArray = new byte[readonlyIndexes.size()];
            for (int i = 0; i < readonlyIndexes.size(); i++) {
                readonlyIndexesArray[i] = readonlyIndexes.get(i);
            }

            messageAddressTableLookups.add(new MessageAddressTableLookup(lookupTable.getKey(), writableIndexesArray, readonlyIndexesArray));
        }
        // @todo hmm maybe immutable?
        return messageAddressTableLookups;
    }
}
