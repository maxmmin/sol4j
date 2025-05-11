package io.github.maxmmin.sol.core.crypto.alt;

import io.github.maxmmin.sol.core.client.type.response.Context;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
@Getter
public class AddressLookupTableAccountWithContext {
    private final Context context;
    private final @Nullable AddressLookupTableAccount lookupTableAccount;
}
