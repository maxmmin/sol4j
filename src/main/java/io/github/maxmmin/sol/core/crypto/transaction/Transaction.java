package io.github.maxmmin.sol.core.crypto.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Transaction {
    private final List<String> signatures;
    private final Message message;
}
