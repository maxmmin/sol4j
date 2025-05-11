package io.github.maxmmin.sol.core.crypto.program;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.SerializingTestsUtil;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.program.AddressLookupTableProgram;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static io.github.maxmmin.sol.program.AddressLookupTableProgram.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AddressLookupTableProgramTest {
    @Test
    public void testCreateLookupTable() {
        PublicKey authorityPubkey = PublicKey.fromBase58("F5WmD6j3N6nR3f4ZbxDz2Sn5pKzqqP5QvYgjQQfSgoL6");
        PublicKey payerPubkey = PublicKey.fromBase58("A3zv6WnRV2rKYzWdty28i6KmAbdujsAVZquCrpnHRRxy");
        BigInteger recentSlot = BigInteger.valueOf(123456);

        CreateLookupTableParams params = new CreateLookupTableParams(authorityPubkey, payerPubkey, recentSlot);
        TransactionInstruction instruction = AddressLookupTableProgram.createLookupTable(params);

        int[] expected  = {0, 0, 0, 0, 64, 226, 1, 0, 0, 0, 0, 0, 254};
        int[] actual = SerializingTestsUtil.toUintArray(instruction.getData());

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFreezeLookupTable() {
        PublicKey authorityPubkey = PublicKey.fromBase58("F5WmD6j3N6nR3f4ZbxDz2Sn5pKzqqP5QvYgjQQfSgoL6");
        PublicKey lookupTableAddress = PublicKey.fromBase58("6kxErATrqJvQNGC6UoKLGzz4dXLZ4RMHrg4aS2ZPZrMh");

        FreezeLookupTableParams params = new FreezeLookupTableParams(authorityPubkey, lookupTableAddress);
        TransactionInstruction instruction = AddressLookupTableProgram.freezeLookupTable(params);

        int[] expected = {1, 0, 0, 0};
        int[] actual = SerializingTestsUtil.toUintArray(instruction.getData());

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testExtendLookupTableWithNonNullPayer() {
        PublicKey authorityPubkey = PublicKey.fromBase58("F5WmD6j3N6nR3f4ZbxDz2Sn5pKzqqP5QvYgjQQfSgoL6");
        PublicKey payerPubkey = PublicKey.fromBase58("A3zv6WnRV2rKYzWdty28i6KmAbdujsAVZquCrpnHRRxy");
        PublicKey lookupTableAddress = PublicKey.fromBase58("6kxErATrqJvQNGC6UoKLGzz4dXLZ4RMHrg4aS2ZPZrMh");

        List<PublicKey> newAddresses = List.of(
                PublicKey.fromBase58("9oRU1qFeLP8nMNJXExjc2HvUWWVp7LfdkPVvJzRuyA3G"),
                PublicKey.fromBase58("B4V5K9tYznWvHytAbKgUAhCNJG7Xw3iBtwvckpQ19e8A")
        );

        ExtendLookupTableParams params = new ExtendLookupTableParams(lookupTableAddress, authorityPubkey, payerPubkey, newAddresses);
        TransactionInstruction instruction = AddressLookupTableProgram.extendLookupTable(params);

        int[] expected = {
                2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 130, 193, 224, 75, 201, 207, 119, 12, 120, 150, 40, 75, 17,
                29, 49, 141, 132, 54, 90, 221, 189, 241, 255, 22, 173, 59, 194, 147, 203, 50, 113, 135, 149, 121, 91, 0,
                69, 120, 232, 66, 213, 22, 230, 156, 62, 209, 249, 74, 67, 97, 37, 17, 146, 60, 23, 96, 144, 24, 19, 185,
                223, 30, 200, 179
        };
        int[] actual = SerializingTestsUtil.toUintArray(instruction.getData());

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testExtendLookupTableWithNullPayer() {
        PublicKey authorityPubkey = PublicKey.fromBase58("F5WmD6j3N6nR3f4ZbxDz2Sn5pKzqqP5QvYgjQQfSgoL6");
        PublicKey lookupTableAddress = PublicKey.fromBase58("6kxErATrqJvQNGC6UoKLGzz4dXLZ4RMHrg4aS2ZPZrMh");

        List<PublicKey> newAddresses = List.of(
                PublicKey.fromBase58("9oRU1qFeLP8nMNJXExjc2HvUWWVp7LfdkPVvJzRuyA3G"),
                PublicKey.fromBase58("B4V5K9tYznWvHytAbKgUAhCNJG7Xw3iBtwvckpQ19e8A")
        );

        ExtendLookupTableParams params = new ExtendLookupTableParams(lookupTableAddress, authorityPubkey, newAddresses);
        TransactionInstruction instruction = AddressLookupTableProgram.extendLookupTable(params);

        int[] expected = {
                2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 130, 193, 224, 75, 201, 207, 119, 12, 120, 150, 40, 75, 17, 29, 49,
                141, 132, 54, 90, 221, 189, 241, 255, 22, 173, 59, 194, 147, 203, 50, 113, 135, 149, 121, 91, 0, 69, 120,
                232, 66, 213, 22, 230, 156, 62, 209, 249, 74, 67, 97, 37, 17, 146, 60, 23, 96, 144, 24, 19, 185, 223, 30,
                200, 179
        };
        int[] actual = SerializingTestsUtil.toUintArray(instruction.getData());

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDeactivateLookupTable() {
        PublicKey authorityPubkey = PublicKey.fromBase58("F5WmD6j3N6nR3f4ZbxDz2Sn5pKzqqP5QvYgjQQfSgoL6");
        PublicKey lookupTableAddress = PublicKey.fromBase58("6kxErATrqJvQNGC6UoKLGzz4dXLZ4RMHrg4aS2ZPZrMh");

        DeactivateLookupTableParams params = new DeactivateLookupTableParams(authorityPubkey, lookupTableAddress);
        TransactionInstruction instruction = AddressLookupTableProgram.deactivateLookupTable(params);

        int[] expected = {3, 0, 0, 0};
        int[] actual = SerializingTestsUtil.toUintArray(instruction.getData());

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testCloseLookupTable() {
        PublicKey authorityPubkey = PublicKey.fromBase58("F5WmD6j3N6nR3f4ZbxDz2Sn5pKzqqP5QvYgjQQfSgoL6");
        PublicKey recipientPubkey = PublicKey.fromBase58("A3zv6WnRV2rKYzWdty28i6KmAbdujsAVZquCrpnHRRxy");
        PublicKey lookupTableAddress = PublicKey.fromBase58("6kxErATrqJvQNGC6UoKLGzz4dXLZ4RMHrg4aS2ZPZrMh");

        CloseLookupTableParams params = new CloseLookupTableParams(authorityPubkey, recipientPubkey, lookupTableAddress);
        TransactionInstruction instruction = AddressLookupTableProgram.closeLookupTable(params);

        int[] expected = {4, 0, 0, 0};
        int[] actual = SerializingTestsUtil.toUintArray(instruction.getData());

        assertArrayEquals(expected, actual);
    }
}
