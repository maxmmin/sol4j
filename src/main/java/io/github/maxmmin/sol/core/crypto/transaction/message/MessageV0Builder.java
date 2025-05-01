package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.program.alt.AddressLookupTableAccount;

import java.util.*;
import java.util.stream.Collectors;

public class MessageV0Builder extends MessageBuilder<MessageV0> {
    private final List<AddressLookupTableAccount> lookupTableAccounts = new ArrayList<>();
    private final Set<PublicKey> programsIdList = new HashSet<>();
    private boolean addEmptyLookups = false;

    public MessageV0Builder setEmptyLookupsAddition(boolean addEmptyLookups) {
        this.addEmptyLookups = addEmptyLookups;
        return this;
    }

    @Override
    public MessageV0Builder setBlockHash(String blockHash) {
        super.setBlockHash(blockHash);
        return this;
    }

    @Override
    public MessageV0Builder setFeePayer(PublicKey feePayer) {
        super.setFeePayer(feePayer);
        return this;
    }

    @Override
    public MessageV0Builder addInstruction(TransactionInstruction transactionInstruction) {
        super.addInstruction(transactionInstruction);
        this.programsIdList.add(transactionInstruction.getProgramId());
        return this;
    }

    @Override
    public MessageV0Builder addInstructions(TransactionInstruction... transactionInstructions) {
        super.addInstructions(transactionInstructions);
        return this;
    }

    public MessageV0Builder addLookupTable(AddressLookupTableAccount lookupTable) {
        lookupTableAccounts.add(lookupTable);
        return this;
    }

    public MessageV0Builder addLookupTables(AddressLookupTableAccount... lookupTables) {
        lookupTableAccounts.addAll(Arrays.asList(lookupTables));
        return this;
    }

    protected boolean isLookupAvailable(AccountMeta accountMeta) {
        PublicKey publicKey = accountMeta.getPubkey();
        return !accountMeta.isSigner() && !programsIdList.contains(publicKey);
    }

    protected Set<PublicKey> getLookupAvailableKeys(List<AccountMeta> staticAccounts) {
        return staticAccounts.stream()
                .filter(this::isLookupAvailable)
                .map(AccountMeta::getPubkey)
                .collect(Collectors.toSet());
    }

    @Override
    protected List<AccountMeta> getInstructionsOrderedAccounts() {
        List<AccountMeta> staticAccounts = new ArrayList<>(super.getInstructionsOrderedAccounts());

        Set<PublicKey> replaceableKeys = getLookupAvailableKeys(staticAccounts);

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
    protected MessageV0 build(MessageBuildArgs messageBuildArgs) {
        List<MessageAddressTableLookup> tableLookups = getLookupAccounts(messageBuildArgs.getAccountMetas());

        int lookupAccountsSize = tableLookups.stream()
                .mapToInt(lookup ->  lookup.getWritableIndexes().length + lookup.getReadonlyIndexes().length)
                .sum();

        List<PublicKey> finalKeys = messageBuildArgs.getAccountKeys().subList(0, messageBuildArgs.getAccountKeys().size() - lookupAccountsSize);
        return new MessageV0(messageBuildArgs.getMessageHeader(), finalKeys, getRecentBlockHash(),
                messageBuildArgs.getCompiledInstructions(), tableLookups);
    }

    private List<MessageAddressTableLookup> getLookupAccounts(List<AccountMeta> allAccounts) {
        Map<PublicKey, AccountMeta> accountMetaMap = allAccounts.stream()
                .filter(this::isLookupAvailable)
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

            if (addEmptyLookups || (writableIndexesArray.length + readonlyIndexesArray.length > 0)) {
                messageAddressTableLookups.add(new MessageAddressTableLookup(lookupTable.getKey(), writableIndexesArray, readonlyIndexesArray));
            }
        }

        return messageAddressTableLookups;
    }
}
