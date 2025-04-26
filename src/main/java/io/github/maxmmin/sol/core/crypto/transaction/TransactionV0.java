package io.github.maxmmin.sol.core.crypto.transaction;

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
}
