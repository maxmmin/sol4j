package io.github.maxmmin.sol.program;

import io.github.maxmmin.sol.core.crypto.transaction.message.AccountMeta;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.core.crypto.BytesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class SystemProgram {
    public static final PublicKey PROGRAM_ID = PublicKey.fromBase58("11111111111111111111111111111111");

    public static final int CREATE_ACCOUNT_INDEX = 0;
    public static final int ASSIGN_INDEX = 1;
    public static final int TRANSFER_INDEX = 2;
    public static final int NONCE_ADVANCE_ACCOUNT_INDEX = 4;
    public static final int NONCE_WITHDRAW_ACCOUNT_INDEX = 5;
    public static final int NONCE_INITIALIZE_ACCOUNT_INDEX = 6;
    public static final int NONCE_AUTHORIZE_ACCOUNT_INDEX = 7;
    public static final int ALLOCATE_INDEX = 8;

    public static TransactionInstruction createAccount(CreateAccountParams createAccountParams) {
        BigInteger lamports = createAccountParams.getLamports();
        if (lamports.compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Lamports must not be negative");

        BigInteger space = createAccountParams.getSpace();
        if (space.compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Space must not be negative");

        ByteBuffer buffer = BytesUtil.allocateLE(4 + 8 + 8 + 32);
        buffer.putInt(0, CREATE_ACCOUNT_INDEX);
        buffer.putLong(4, createAccountParams.getLamports().longValue());
        buffer.putLong(12, createAccountParams.getSpace().longValue());
        BytesUtil.putPubkey(buffer, 20, createAccountParams.getProgramId());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(createAccountParams.getFromPubkey(), true, true),
                new AccountMeta(createAccountParams.getNewAccountPubkey(), true, true)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction assign(AssignParams assignParams) {
        ByteBuffer buffer = BytesUtil.allocateLE(4 + 32);
        buffer.putInt(0, ASSIGN_INDEX);
        BytesUtil.putPubkey(buffer, 4, assignParams.getProgramId());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(new AccountMeta(assignParams.getAccountPubkey(), true, true));

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction transfer(TransferParams transferParams) {
        if (transferParams.getLamports().compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("Lamports cannot be negative");

        ByteBuffer buffer = BytesUtil.allocateLE(4 + 8);
        buffer.putInt(0, TRANSFER_INDEX);
        buffer.putLong(4, transferParams.getLamports().longValue());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(transferParams.getFrom(), true, true),
                new AccountMeta(transferParams.getTo(), false, true)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction nonceAdvance(NonceAdvanceParams nonceAdvanceParams) {
        ByteBuffer buffer = BytesUtil.allocateLE(4);
        buffer.putInt(0, NONCE_ADVANCE_ACCOUNT_INDEX);
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(nonceAdvanceParams.getNoncePubkey(), false, true),
                new AccountMeta(SysVar.RECENT_BLOCKHASHES_PUBKEY, false, false),
                new AccountMeta(nonceAdvanceParams.getAuthorizedPubkey(), true, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction nonceWithdraw(NonceWithdrawParams nonceWithdrawParams) {
        ByteBuffer buffer = BytesUtil.allocateLE(4 + 8);
        buffer.putInt(0, NONCE_WITHDRAW_ACCOUNT_INDEX);
        buffer.putLong(4, nonceWithdrawParams.getLamports().longValue());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(nonceWithdrawParams.getNoncePubkey(), false, true),
                new AccountMeta(nonceWithdrawParams.getToPubkey(), false, true),
                new AccountMeta(SysVar.RECENT_BLOCKHASHES_PUBKEY, false, false),
                new AccountMeta(SysVar.RENT_PUBKEY, false, false),
                new AccountMeta(nonceWithdrawParams.getAuthorizedPubkey(), true, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction nonceInitialize(NonceInitializeParams nonceInitializeParams) {
        ByteBuffer buffer = BytesUtil.allocateLE(4 + 32);
        buffer.putInt(0, NONCE_INITIALIZE_ACCOUNT_INDEX);
        BytesUtil.putPubkey(buffer, 4, nonceInitializeParams.getAuthorizedPubkey());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(nonceInitializeParams.getNoncePubkey(), false, true),
                new AccountMeta(SysVar.RECENT_BLOCKHASHES_PUBKEY, false, false),
                new AccountMeta(SysVar.RENT_PUBKEY, false, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction nonceAuthorize(NonceAuthorizeParams nonceAuthorizeParams) {
        ByteBuffer buffer = BytesUtil.allocateLE(4 + 32);
        buffer.putInt(0, NONCE_AUTHORIZE_ACCOUNT_INDEX);
        BytesUtil.putPubkey(buffer, 4, nonceAuthorizeParams.getNewAuthorizedPubkey());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(nonceAuthorizeParams.getNoncePubkey(), false, true),
                new AccountMeta(nonceAuthorizeParams.getAuthorizedPubkey(), true, false)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
    }

    public static TransactionInstruction allocate(AllocateParams allocateParams) {
        ByteBuffer buffer = BytesUtil.allocateLE(4 + 8);
        buffer.putInt(ALLOCATE_INDEX);
        buffer.putLong(4, allocateParams.getSpace().longValue());
        byte[] data = buffer.array();

        List<AccountMeta> accounts = List.of(
                new AccountMeta(allocateParams.getAccountPubkey(), true, true)
        );

        return new TransactionInstruction(PROGRAM_ID, accounts, data);
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

    @Getter
    @RequiredArgsConstructor
    public static class NonceAdvanceParams {
        private final PublicKey noncePubkey;
        private final PublicKey authorizedPubkey;
    }

    @Getter
    @RequiredArgsConstructor
    public static class NonceWithdrawParams {
        private final PublicKey noncePubkey;
        private final PublicKey authorizedPubkey;
        private final PublicKey toPubkey;
        private final BigInteger lamports;
    }

    @Getter
    @RequiredArgsConstructor
    public static class NonceInitializeParams {
        private final PublicKey noncePubkey;
        private final PublicKey authorizedPubkey;
    }

    @Getter
    @RequiredArgsConstructor
    public static class NonceAuthorizeParams {
        private final PublicKey noncePubkey;
        private final PublicKey authorizedPubkey;
        private final PublicKey newAuthorizedPubkey;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AllocateParams {
        private final PublicKey accountPubkey;
        private final BigInteger space;
    }

}
