package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Account;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.message.Message;
import io.github.maxmmin.sol.program.SystemProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class LegacyTransactionBuilderTest {
    @Test
    public void testSimpleTxBuild() {
        Account sender = Account.generate();
        PublicKey recipient = PublicKey.fromBase58("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
        BigInteger lamports = BigInteger.valueOf(100);

        Message message = Message.builder()
                .setBlockHash("EkSnNWid2cvwEVnVx9aBqawnmiCNiDgp3gUdkDPTKN1N")
                .setFeePayer(sender.getPublicKey())
                .addInstruction(SystemProgram.transfer(new SystemProgram.TransferParams(sender.getPublicKey(), recipient, lamports)))
                .build();

        Transaction tx = Transaction.build(message, sender);

        Assertions.assertEquals(1, tx.getSignatures().size());
    }
}
