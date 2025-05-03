package io.github.maxmmin.sol.program.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.PublicKeyUtils;
import io.github.maxmmin.sol.core.crypto.exception.NonceNotFoundException;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.core.crypto.transaction.message.AccountMeta;
import io.github.maxmmin.sol.program.SystemProgram;
import io.github.maxmmin.sol.util.SerializationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class AddressLookupTableProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("AddressLookupTab1e1111111111111111111111111");

    public static int CREATE_LOOKUP_TABLE_INDEX = 0;
    public static int FREEZE_LOOKUP_TABLE_INDEX = 1;
    public static int EXTEND_LOOKUP_TABLE_INDEX = 2;
    public static int DEACTIVATE_LOOKUP_TABLE_INDEX = 3;
    public static int CLOSE_LOOKUP_TABLE = 4;

    public static TransactionInstruction createLookupTable(CreateLookupTableParams params) {
        if (params.getRecentSlot().compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Recent slot can't be negative");

        PublicKeyUtils.PubkeyWithNonce pubkeyWithNonce;
        byte[] recentSlotBytes = SerializationUtils.serializeUint64LE(params.getRecentSlot());
        try {
            pubkeyWithNonce = PublicKeyUtils.findProgramAddress(
                    new byte[][]{params.getAuthority().getBytes(), SerializationUtils.serializeUint64LE(params.getRecentSlot())},
                    PROGRAM_ID
            );
        } catch (NonceNotFoundException e) {
            throw new RuntimeException("Could not found the nonce for PDA", e);
        }

        ByteBuffer buffer = SerializationUtils.allocateLE(4 + 8 + 4);
        buffer.putInt(0, CREATE_LOOKUP_TABLE_INDEX);
        SerializationUtils.putBytes(buffer, 4, recentSlotBytes);
        buffer.put(pubkeyWithNonce.getNonce());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(PROGRAM_ID, false, true),
                new AccountMeta(params.getAuthority(), true, false),
                new AccountMeta(params.getPayer(), true, true),
                new AccountMeta(SystemProgram.PROGRAM_ID, false, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction freezeLookupTable(FreezeLookupTableParams params) {
        byte[] data = SerializationUtils.allocateLE(4).putInt(FREEZE_LOOKUP_TABLE_INDEX).array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(PROGRAM_ID, false, true),
                new AccountMeta(params.getAuthority(), true, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateLookupTableParams {
        private final PublicKey authority;
        private final PublicKey payer;
        private final BigInteger recentSlot;
    }

    @Getter
    @RequiredArgsConstructor
    public static class FreezeLookupTableParams {
        private final PublicKey lookupTable;
        private final PublicKey authority;
    }
}
