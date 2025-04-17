package io.github.maxmmin.sol.core.crypto.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TransactionBuilder {
    private final List<String> signatures = new ArrayList<>();
    private final Message message;

    public TransactionBuilder(Message message) {
        this.message = message;
    }

    public TransactionBuilder addSignature(String signatureToAdd) {
        signatures.add(signatureToAdd);
        return this;
    }

    public TransactionBuilder addSignatures(Collection<String> signaturesToAdd) {
        signatures.addAll(signaturesToAdd);
        return this;
    }

    public Transaction build () throws IllegalStateException {
        if (getSignersCount() != signatures.size()) throw new IllegalStateException("Signers and signatures sizes diff");
        return new Transaction(signatures, message);
    }

    protected int getSignersCount() {
        return Byte.toUnsignedInt(message.getMessageHeader().getNumRequiredSignatures());
    }
}
