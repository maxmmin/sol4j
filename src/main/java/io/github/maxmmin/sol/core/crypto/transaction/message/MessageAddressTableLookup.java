package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageAddressTableLookup {
    private final PublicKey accountKey;
    private final byte[] writableIndexes;
    private final byte[] readonlyIndexes;

    public boolean isEmpty() {
        return writableIndexes.length + readonlyIndexes.length == 0;
    }
}
