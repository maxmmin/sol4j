package io.github.maxmmin.sol.core.crypto.transaction;

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
}
