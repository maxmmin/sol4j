package io.github.maxmmin.sol.core.crypto;

import io.github.maxmmin.sol.core.crypto.transaction.Message;
import io.github.maxmmin.sol.core.crypto.transaction.MessageBuilder;
import io.github.maxmmin.sol.core.crypto.transaction.MessageSerializer;
import io.github.maxmmin.sol.program.SystemProgram;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    @Test
    public void serializeMessage() {
        PublicKey signer = PublicKey.fromBase58("11111111111111111111111111111111");

        PublicKey from = PublicKey.fromBase58("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
        PublicKey to = PublicKey.fromBase58("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
        BigInteger lamports = BigInteger.valueOf(3000);
        SystemProgram.TransferParams transferParams = new SystemProgram.TransferParams(from, to, lamports);

        Message message = new MessageBuilder()
                .setFeePayer(signer)
                .setBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
                .addInstruction(SystemProgram.transfer(transferParams))
                .build();

        assertArrayEquals(new int[] { 1, 0, 1, 3, 6, 26, 217, 208, 83, 135, 21, 72, 83, 126, 222, 62, 38, 24, 73, 163,
                223, 183, 253, 2, 250, 188, 117, 178, 35, 200, 228, 106, 219, 133, 61, 12, 235, 122, 188, 208, 216, 117,
                235, 194, 109, 161, 177, 129, 163, 51, 155, 62, 242, 163, 22, 149, 187, 122, 189, 188, 103, 130, 115,
                188, 173, 205, 229, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 203, 226, 136, 193, 153, 148, 240, 50, 230, 98, 9, 79, 221, 179, 243, 174, 90, 67,
                104, 169, 6, 187, 165, 72, 36, 156, 19, 57, 132, 38, 69, 245, 1, 2, 2, 0, 1, 12, 2, 0, 0, 0, 184, 11, 0,
                0, 0, 0, 0, 0 }, toUintArray(MessageSerializer.serialize(message)));
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
