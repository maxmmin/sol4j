package io.github.maxmmin.sol.core.crypto.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Transaction {
    private final List<String> signatures;
    private final Message message;

    public Transaction(List<String> signatures, Message message) {
        this.signatures = List.copyOf(signatures);
        this.message = message;
    }
}
