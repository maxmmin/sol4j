package io.github.maxmmin.sol.core.crypto.transaction;

import java.util.Arrays;
import java.util.List;

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
}
