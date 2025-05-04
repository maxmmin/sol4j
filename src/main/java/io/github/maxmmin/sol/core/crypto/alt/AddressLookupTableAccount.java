package io.github.maxmmin.sol.core.crypto.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
public class AddressLookupTableAccount {
    private final static BigInteger ACTIVE_FLAG = new BigInteger("ffffffffffffffff", 16);

    private final PublicKey key;
    private final AddressLookupTableState state;

    public boolean isActive() {
        return getState().getDeactivationSlot().equals(ACTIVE_FLAG);
    }
}
