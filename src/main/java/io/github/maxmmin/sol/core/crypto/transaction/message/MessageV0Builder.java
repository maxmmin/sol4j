package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.program.alt.AddressLookupTableAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected MessageV0 build(MessageComponents messageComponents) {
        return null;
    }
}
