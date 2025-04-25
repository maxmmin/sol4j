package io.github.maxmmin.sol.program.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AddressLookupTableAccount {
    private final PublicKey key;
    private final AddressLookupTableState state;

    @Getter
    @RequiredArgsConstructor
    public static class AddressLookupTableState {
        private final BigInteger deactivationSlot;
        private final BigInteger lastExtendedSlot;
        private final BigInteger lastExtendedSlotStartIndex;
        private final PublicKey authority;
        private final List<PublicKey> addresses;
    }
}
