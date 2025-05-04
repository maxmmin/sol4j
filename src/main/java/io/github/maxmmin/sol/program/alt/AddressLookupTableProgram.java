package io.github.maxmmin.sol.program.alt;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.PublicKeyUtil;
import io.github.maxmmin.sol.core.crypto.exception.NonceNotFoundException;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.core.crypto.transaction.message.AccountMeta;
import io.github.maxmmin.sol.program.SystemProgram;
import io.github.maxmmin.sol.core.crypto.BytesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AddressLookupTableProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("AddressLookupTab1e1111111111111111111111111");

    public static int CREATE_LOOKUP_TABLE_INDEX = 0;
    public static int FREEZE_LOOKUP_TABLE_INDEX = 1;
    public static int EXTEND_LOOKUP_TABLE_INDEX = 2;
    public static int DEACTIVATE_LOOKUP_TABLE_INDEX = 3;
    public static int CLOSE_LOOKUP_TABLE_INDEX = 4;

    public static TransactionInstruction createLookupTable(CreateLookupTableParams params) {
        if (params.getRecentSlot().compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Recent slot can't be negative");

        PublicKeyUtil.PubkeyWithNonce pubkeyWithNonce;
        byte[] recentSlotBytes = BytesUtil.serializeUint64LE(params.getRecentSlot());
        try {
            pubkeyWithNonce = PublicKeyUtil.findProgramAddress(
                    new byte[][]{params.getAuthority().getBytes(), BytesUtil.serializeUint64LE(params.getRecentSlot())},
                    PROGRAM_ID
            );
        } catch (NonceNotFoundException e) {
            throw new RuntimeException("Could not found the nonce for PDA", e);
        }

        ByteBuffer buffer = BytesUtil.allocateLE(4 + 8 + 1);
        buffer.putInt(0, CREATE_LOOKUP_TABLE_INDEX);
        BytesUtil.putBytes(buffer, 4, recentSlotBytes);
        buffer.put(12, pubkeyWithNonce.getNonce());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(pubkeyWithNonce.getAddress(), false, true),
                new AccountMeta(params.getAuthority(), true, false),
                new AccountMeta(params.getPayer(), true, true),
                new AccountMeta(SystemProgram.PROGRAM_ID, false, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction freezeLookupTable(FreezeLookupTableParams params) {
        byte[] data = BytesUtil.allocateLE(4).putInt(FREEZE_LOOKUP_TABLE_INDEX).array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(params.getLookupTable(), false, true),
                new AccountMeta(params.getAuthority(), true, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction extendLookupTable(ExtendLookupTableParams params) {
        List<PublicKey> addresses = params.getAddresses();
        int addressesSpace = addresses.size() * 32;
        ByteBuffer buffer = BytesUtil.allocateLE(4 + 8 + addressesSpace)
                .putInt(0, EXTEND_LOOKUP_TABLE_INDEX);

        BytesUtil.putUint64(buffer, 4, BigInteger.valueOf(addresses.size()));
        int offset = 12;
        for (PublicKey address : addresses) {
            BytesUtil.putPubkey(buffer, offset, address);
            offset += 32;
        }

        byte[] data = buffer.array();

        List<AccountMeta> accounts = new ArrayList<>();
        accounts.add(new AccountMeta(params.getLookupTable(), false, true));
        accounts.add(new AccountMeta(params.getAuthority(), true, false));
        if (params.getPayer() != null) {
            accounts.add(new AccountMeta(params.getPayer(), true, true));
            accounts.add(new AccountMeta(SystemProgram.PROGRAM_ID, false, false));
        }

        return new TransactionInstruction(PROGRAM_ID, List.copyOf(accounts), data);
    }

    public static TransactionInstruction deactivateLookupTable(DeactivateLookupTableParams params) {
        byte[] data = BytesUtil.allocateLE(4).putInt(DEACTIVATE_LOOKUP_TABLE_INDEX).array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(params.getLookupTable(), false, true),
                new AccountMeta(params.getAuthority(), true, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction closeLookupTable(CloseLookupTableParams params) {
        byte[] data = BytesUtil.allocateLE(4).putInt(CLOSE_LOOKUP_TABLE_INDEX).array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(params.getLookupTable(), false, true),
                new AccountMeta(params.getAuthority(), true, false),
                new AccountMeta(params.getRecipient(), false, true)
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

    @Getter
    @RequiredArgsConstructor
    public static class ExtendLookupTableParams {
        private final PublicKey lookupTable;
        private final PublicKey authority;
        private final @Nullable PublicKey payer;
        private final List<PublicKey> addresses;

        public ExtendLookupTableParams(PublicKey lookupTable, PublicKey authority, List<PublicKey> addresses) {
            this.lookupTable = lookupTable;
            this.authority = authority;
            this.payer = null;
            this.addresses = addresses;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class DeactivateLookupTableParams {
        private final PublicKey lookupTable;
        private final PublicKey authority;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CloseLookupTableParams {
        private final PublicKey lookupTable;
        private final PublicKey authority;
        private final PublicKey recipient;
    }
}
