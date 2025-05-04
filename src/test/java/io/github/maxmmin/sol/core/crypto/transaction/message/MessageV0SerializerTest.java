package io.github.maxmmin.sol.core.crypto.transaction.message;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.SerializingTestsUtil;
import io.github.maxmmin.sol.program.SystemProgram;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MessageV0SerializerTest {
    @Test
    public void testSimpleMessageSerializing() {
        PublicKey signer = PublicKey.fromBase58("11111111111111111111111111111111");

        AddressLookupTableAccount.AddressLookupTableState state = new AddressLookupTableAccount.AddressLookupTableState(
                new BigInteger("ffffffffffffffff", 16),
                BigInteger.ZERO,
                BigInteger.ZERO,
                null,
                List.of(SystemProgram.PROGRAM_ID)
        );
        AddressLookupTableAccount tableAccount = new AddressLookupTableAccount(PublicKey.fromBase58("11111111111111111111111111111111"), state);

        PublicKey from = PublicKey.fromBase58("BNzpkiBcpXNbH5ubrRBGVJex62df1kc1DWwu2kkxaUMX");
        PublicKey to = PublicKey.fromBase58("6tcxA4dVKGJecuXRHsa9NmW1JKXAJBtuPNAVoh2knm7j");
        BigInteger lamports = BigInteger.valueOf(500);

        SystemProgram.TransferParams transferParams = new SystemProgram.TransferParams(from, to, lamports);
        MessageV0 message = new MessageV0Builder()
                .setFeePayer(signer)
                .setBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
                .addInstruction(SystemProgram.transfer(transferParams))
                .addLookupTable(tableAccount)
                .build();

        int[] expected = {
                128, 2, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 154, 55, 114, 130, 251, 232, 203, 4, 208, 61, 153, 42, 179, 56, 141, 170, 245, 129, 51, 180, 244,
                214, 112, 162, 189, 217, 74, 240, 231, 141, 114, 42, 87, 131, 180, 174, 96, 169, 112, 113, 104, 95, 143,
                143, 181, 149, 200, 213, 105, 20, 58, 104, 77, 239, 166, 88, 233, 97, 143, 185, 243, 93, 11, 14, 203, 226,
                136, 193, 153, 148, 240, 50, 230, 98, 9, 79, 221, 179, 243, 174, 90, 67, 104, 169, 6, 187, 165, 72, 36,
                156, 19, 57, 132, 38, 69, 245, 1, 0, 2, 1, 2, 12, 2, 0, 0, 0, 244, 1, 0, 0, 0, 0, 0, 0, 0
        };

        int[] messageBytes = SerializingTestsUtil.toUintArray(MessageSerializer.getSerializerV0().serialize(message));

        assertArrayEquals(expected, messageBytes);
    }

    @Test
    public void testLookupAbleKeyMessageSerializing() {
        PublicKey signer = PublicKey.fromBase58("11111111111111111111111111111111");
        PublicKey recipient = PublicKey.fromBase58("6tcxA4dVKGJecuXRHsa9NmW1JKXAJBtuPNAVoh2knm7j");

        AddressLookupTableAccount.AddressLookupTableState state = new AddressLookupTableAccount.AddressLookupTableState(
                new BigInteger("ffffffffffffffff", 16),
                BigInteger.ZERO,
                BigInteger.ZERO,
                null,
                List.of(SystemProgram.PROGRAM_ID, recipient)
        );
        AddressLookupTableAccount tableAccount = new AddressLookupTableAccount(PublicKey.fromBase58("11111111111111111111111111111111"), state);

        PublicKey from = PublicKey.fromBase58("BNzpkiBcpXNbH5ubrRBGVJex62df1kc1DWwu2kkxaUMX");
        BigInteger lamports = BigInteger.valueOf(500);

        SystemProgram.TransferParams transferParams = new SystemProgram.TransferParams(from, recipient, lamports);
        MessageV0 message = new MessageV0Builder()
                .setFeePayer(signer)
                .setBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
                .addInstruction(SystemProgram.transfer(transferParams))
                .addLookupTable(tableAccount)
                .build();

        int[] expected = {
                128, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 154, 55, 114, 130, 251, 232, 203, 4, 208, 61, 153, 42, 179, 56, 141, 170, 245, 129, 51, 180, 244,
                214, 112, 162, 189, 217, 74, 240, 231, 141, 114, 42, 203, 226, 136, 193, 153, 148, 240, 50, 230, 98, 9,
                79, 221, 179, 243, 174, 90, 67, 104, 169, 6, 187, 165, 72, 36, 156, 19, 57, 132, 38, 69, 245, 1, 0, 2,
                1, 2, 12, 2, 0, 0, 0, 244, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0
        };

        int[] messageBytes = SerializingTestsUtil.toUintArray(MessageSerializer.getSerializerV0().serialize(message));

        assertArrayEquals(expected, messageBytes);
    }

    @Test
    public void testLookupAbleKeysMessageSerializing() {
        PublicKey signer = PublicKey.fromBase58("5tzFkiKscXHK5ZXCGbXZxdw7gTjjD1mBwuoFbhUvuAi9");
        PublicKey recipient = PublicKey.fromBase58("6tcxA4dVKGJecuXRHsa9NmW1JKXAJBtuPNAVoh2knm7j");

        AddressLookupTableAccount.AddressLookupTableState state = new AddressLookupTableAccount.AddressLookupTableState(
                new BigInteger("ffffffffffffffff", 16),
                BigInteger.ZERO,
                BigInteger.ZERO,
                null,
                List.of(SystemProgram.PROGRAM_ID, recipient)
        );
        AddressLookupTableAccount tableAccount = new AddressLookupTableAccount(PublicKey.fromBase58("11111111111111111111111111111111"), state);

        PublicKey from = PublicKey.fromBase58("BNzpkiBcpXNbH5ubrRBGVJex62df1kc1DWwu2kkxaUMX");
        BigInteger lamports = BigInteger.valueOf(500);

        SystemProgram.TransferParams transferParams = new SystemProgram.TransferParams(from, recipient, lamports);
        MessageV0 message = new MessageV0Builder()
                .setFeePayer(signer)
                .setBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
                .addInstruction(SystemProgram.transfer(transferParams))
                .addLookupTable(tableAccount)
                .build();

        int[] expected = {
                128, 2, 0, 1, 3, 72, 192, 27, 80, 89, 0, 84, 85, 217, 220, 176, 198, 188, 236, 220, 180, 251, 91, 46,
                171, 193, 169, 168, 43, 87, 57, 43, 170, 164, 15, 4, 230, 154, 55, 114, 130, 251, 232, 203, 4, 208, 61,
                153, 42, 179, 56, 141, 170, 245, 129, 51, 180, 244, 214, 112, 162, 189, 217, 74, 240, 231, 141, 114, 42,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 203, 226,
                136, 193, 153, 148, 240, 50, 230, 98, 9, 79, 221, 179, 243, 174, 90, 67, 104, 169, 6, 187, 165, 72, 36,
                156, 19, 57, 132, 38, 69, 245, 1, 2, 2, 1, 3, 12, 2, 0, 0, 0, 244, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0
        };

        int[] messageBytes = SerializingTestsUtil.toUintArray(MessageSerializer.getSerializerV0().serialize(message));

        assertArrayEquals(expected, messageBytes);
    }

}
