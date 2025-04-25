package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.program.alt.AddressLookupTableAccount;

import java.util.ArrayList;
import java.util.List;

public class V0MessageBuilder {
    private String recentBlockHash;
    private PublicKey feePayer;
    private final List<TransactionInstruction> transactionInstructions = new ArrayList<>();
    private final List<AddressLookupTableAccount> lookupTables = new ArrayList<>();
}
