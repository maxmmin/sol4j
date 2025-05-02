package io.github.maxmmin.sol.program.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.PublicKeyUtils;
import io.github.maxmmin.sol.util.BufferUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.Base64;

public class AddressLookupTableProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("AddressLookupTab1e1111111111111111111111111");

    public static int CREATE_LOOKUP_TABLE_INDEX = 0;
    public static int FREEZE_LOOKUP_TABLE_INDEX = 1;
    public static int EXTEND_LOOKUP_TABLE_INDEX = 2;
    public static int DEACTIVATE_LOOKUP_TABLE_INDEX = 3;
    public static int CLOSE_LOOKUP_TABLE = 4;

    public static void createLookupTable(CreateLookupTableParams params) {
        BufferUtil.allocateLE(8).putLong(params.getRecentSlot());
        PublicKeyUtils.findProgramAddress(params.getAuthority().getBytes(), Base64.getEncoder().encode(params.getRecentSlot().toByteArray()))
    }

    //@todo write BigInt u64 enc
    private

    @Getter
    @RequiredArgsConstructor
    public static class CreateLookupTableParams {
        private final PublicKey authority;
        private final PublicKey payer;
        private final BigInteger recentSlot;
    }
}
