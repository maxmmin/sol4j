package io.github.maxmmin.sol.program;

import io.github.maxmmin.sol.core.crypto.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class SystemProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("11111111111111111111111111111111");

    public static TransactionInstruction transfer(TransferParams transferParams) {
        if (transferParams.getLamports().compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Lamports cannot be negative");

        List<AccountMeta> accounts = List.of(
                new AccountMeta(transferParams.getFrom(), true, true),
                new AccountMeta(transferParams.getTo(), false, true)
        );

        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(Index.TRANSFER);
        buffer.putLong(4, transferParams.getLamports().longValue());
        byte[] data = buffer.array();

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }


    private static class Index {
        public static final int TRANSFER = 2;
    }

    @Getter
    @RequiredArgsConstructor
    public static class TransferParams {
        private final PublicKey from;
        private final PublicKey to;
        private final BigInteger lamports;
    }
}
