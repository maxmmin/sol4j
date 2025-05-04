package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Account;
import io.github.maxmmin.sol.core.crypto.transaction.message.Message;
import lombok.Getter;

import java.util.List;

@Getter
public class Transaction {
    private final List<String> signatures;
    private final Message message;

    public Transaction(List<String> signatures, Message message) {
        this.signatures = List.copyOf(signatures);
        this.message = message;
    }

    public static LegacyTransactionBuilder builder(Message message) {
        return new LegacyTransactionBuilder(message);
    }

    public static Transaction build(Message message, List<Account> signers) {
        LegacyTransactionBuilder builder = builder(message);
        signers.forEach(builder::sign);
        return builder.build();
    }

    public static Transaction build(Message message, Account signer) {
        return builder(message)
                .sign(signer)
                .build();
    }
}
