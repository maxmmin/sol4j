package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Account;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageV0;
import lombok.Getter;

import java.util.List;

@Getter
public class TransactionV0 {
    private final List<String> signatures;
    private final MessageV0 message;

    public TransactionV0(List<String> signatures, MessageV0 message) {
        this.signatures = List.copyOf(signatures);
        this.message = message;
    }

    public static TransactionV0Builder builder(MessageV0 message) {
        return new TransactionV0Builder(message);
    }

    public static TransactionV0 build(MessageV0 message, List<Account> signers) {
        TransactionV0Builder builder = builder(message);
        signers.forEach(builder::sign);
        return builder.build();
    }

    public static TransactionV0 build(MessageV0 message, Account signer) {
        return builder(message)
                .sign(signer)
                .build();
    }

}
