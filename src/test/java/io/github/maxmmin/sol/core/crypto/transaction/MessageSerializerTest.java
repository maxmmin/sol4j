package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.message.LegacyMessageBuilder;
import io.github.maxmmin.sol.core.crypto.transaction.message.Message;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageSerializer;
import io.github.maxmmin.sol.program.SystemProgram;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MessageSerializerTest {
    @Test
    public void serializeMessage() {
        PublicKey signer = PublicKey.fromBase58("11111111111111111111111111111111");

        PublicKey from = PublicKey.fromBase58("BNzpkiBcpXNbH5ubrRBGVJex62df1kc1DWwu2kkxaUMX");
        PublicKey to = PublicKey.fromBase58("6tcxA4dVKGJecuXRHsa9NmW1JKXAJBtuPNAVoh2knm7j");
        BigInteger lamports = BigInteger.valueOf(500);
        SystemProgram.TransferParams transferParams = new SystemProgram.TransferParams(from, to, lamports);

        Message message = new LegacyMessageBuilder()
                .setFeePayer(signer)
                .setBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
                .addInstruction(SystemProgram.transfer(transferParams))
                .build();

        int [] expected = {
                2, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 154, 55, 114, 130, 251, 232, 203, 4, 208, 61, 153, 42, 179, 56, 141, 170, 245, 129, 51, 180,
                244, 214, 112, 162, 189, 217, 74, 240, 231, 141, 114, 42, 87, 131, 180, 174, 96, 169, 112, 113, 104,
                95, 143, 143, 181, 149, 200, 213, 105, 20, 58, 104, 77, 239, 166, 88, 233, 97, 143, 185, 243, 93, 11,
                14, 203, 226, 136, 193, 153, 148, 240, 50, 230, 98, 9, 79, 221, 179, 243, 174, 90, 67, 104, 169, 6, 187,
                165, 72, 36, 156, 19, 57, 132, 38, 69, 245, 1, 0, 2, 1, 2, 12, 2, 0, 0, 0, 244, 1, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expected, toUintArray(MessageSerializer.serialize(message)));
    }

    protected int[] toUintArray(byte[] source) {
        int[] target = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            // cut off filled '1' if source byte was negative
            target[i] = source[i] & 0xFF;
        }
        return target;
    }
}
