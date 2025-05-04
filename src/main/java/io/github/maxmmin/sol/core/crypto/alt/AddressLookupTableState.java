package io.github.maxmmin.sol.core.crypto.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AddressLookupTableState {
    private final BigInteger deactivationSlot;
    private final BigInteger lastExtendedSlot;
    private final int lastExtendedSlotStartIndex;
    private final PublicKey authority;
    private final List<PublicKey> addresses;
}
