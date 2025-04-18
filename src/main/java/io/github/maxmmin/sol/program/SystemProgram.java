package io.github.maxmmin.sol.program;

import io.github.maxmmin.sol.core.crypto.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.util.BufferUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class SystemProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("11111111111111111111111111111111");

    public static TransactionInstruction create(CreateAccountParams createAccountParams) {
        BigInteger lamports = createAccountParams.getLamports();
        if (lamports.compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Lamports must not be negative");

        BigInteger space = createAccountParams.getSpace();
        if (space.compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Space must not be negative");

        ByteBuffer buffer = ByteBuffer.allocate(4 + 8 + 8 + 32);
        buffer.putInt(0, Index.CREATE);
        buffer.putLong(4, createAccountParams.getLamports().longValue());
        buffer.putLong(12, createAccountParams.getSpace().longValue());
        BufferUtil.putPubkey(buffer, 20, createAccountParams.getProgramId());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(createAccountParams.getFromPubkey(), true, true),
                new AccountMeta(createAccountParams.getNewAccountPubkey(), true, true)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction assign(AssignParams assignParams) {
        ByteBuffer buffer = BufferUtil.allocateLE(4 + 32);
        buffer.putInt(0, Index.ASSIGN);
        BufferUtil.putPubkey(buffer, 4, assignParams.getProgramId());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(new AccountMeta(assignParams.getAccountPubkey(), true, true));

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction transfer(TransferParams transferParams) {
        if (transferParams.getLamports().compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Lamports cannot be negative");

        List<AccountMeta> accounts = List.of(
                new AccountMeta(transferParams.getFrom(), true, true),
                new AccountMeta(transferParams.getTo(), false, true)
        );

        ByteBuffer buffer = BufferUtil.allocateLE(4 + 8);
        buffer.putInt(0, Index.TRANSFER);
        buffer.putLong(4, transferParams.getLamports().longValue());
        byte[] data = buffer.array();

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    private static class Index {
        public static final int CREATE = 0;
        public static final int ASSIGN = 1;
        public static final int TRANSFER = 2;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateAccountParams {
        private final PublicKey fromPubkey;
        private final PublicKey newAccountPubkey;
        private final BigInteger lamports;
        private final BigInteger space;
        private final PublicKey programId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AssignParams {
        private final PublicKey accountPubkey;
        private final PublicKey programId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class TransferParams {
        private final PublicKey from;
        private final PublicKey to;
        private final BigInteger lamports;
    }
}
