package io.github.maxmmin.sol.program.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.PublicKeyUtils;
import io.github.maxmmin.sol.core.crypto.exception.NonceNotFoundException;
import io.github.maxmmin.sol.util.BufferUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

public class AddressLookupTableProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("AddressLookupTab1e1111111111111111111111111");

    public static int CREATE_LOOKUP_TABLE_INDEX = 0;
    public static int FREEZE_LOOKUP_TABLE_INDEX = 1;
    public static int EXTEND_LOOKUP_TABLE_INDEX = 2;
    public static int DEACTIVATE_LOOKUP_TABLE_INDEX = 3;
    public static int CLOSE_LOOKUP_TABLE = 4;

    public static void createLookupTable(CreateLookupTableParams params) {
        if (params.getRecentSlot().compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Recent slot can't be negative");

        PublicKeyUtils.PubkeyWithNonce pubkeyWithNonce;
        try {
            pubkeyWithNonce = PublicKeyUtils.findProgramAddress(
                    new byte[][]{params.getAuthority().getBytes(), serializeUint64LE(params.getRecentSlot())},
                    PROGRAM_ID
            );
        } catch (NonceNotFoundException e) {
            throw new RuntimeException("Could not found the nonce for PDA", e);
        }

        // @todo
    }

    private static byte[] serializeUint64LE(BigInteger uint64) {
        byte[] bytes = uint64.toByteArray();
        int uintLength = bytes[0] == 0 ? bytes.length - 1 : bytes.length;
        if (uintLength > 8) throw new IllegalArgumentException("Number is too large for uint64");
        return BufferUtil.allocateLE(8).putLong(uint64.longValue()).array();
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateLookupTableParams {
        private final PublicKey authority;
        private final PublicKey payer;
        private final BigInteger recentSlot;
    }
}
