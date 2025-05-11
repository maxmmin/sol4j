package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Account;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.sign.MessageSignException;
import io.github.maxmmin.sol.core.crypto.sign.MessageSigner;
import io.github.maxmmin.sol.core.crypto.transaction.message.Message;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageV0;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TransactionBuilder<T, M> {
    private final Map<PublicKey, String> signatures;
    private final M message;
    private final MessageSigner<M> messageSigner;
    private final byte[] messageBytes;


    public TransactionBuilder(M message, byte[] serializedMessage,
                              MessageSigner<M> messageSigner) {
        this.message = message;
        this.messageSigner = messageSigner;
        this.messageBytes = serializedMessage;
        this.signatures = new HashMap<>();
        getSigners(message).forEach(signer -> signatures.put(signer, null));
    }

    public TransactionBuilder<T, M> sign(Account account) {
        PublicKey publicKey = account.getPublicKey();
        if (!signatures.containsKey(publicKey)) throw new IllegalArgumentException("Account isn't related to any signer: " + publicKey);
        try {
            String signature = messageSigner.sign(messageBytes, account.getSecretKey());
            signatures.put(publicKey, signature);
        } catch (MessageSignException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public T build () throws IllegalStateException {
        List<String> signaturesList = new ArrayList<>(signatures.size());
        List<PublicKey> signers = getSigners(message);
        for (int i = 0; i < signers.size(); i++) {
            PublicKey signer = signers.get(i);
            String signature = signatures.get(signer);
            if (signature == null) throw new IllegalStateException(
                    String.format("No signature found for signer %s (index %d)", signer.toString(), i)
            );
            signaturesList.add(signature);
        }
        TransactionComponents transactionComponents = new TransactionComponents(signers, signaturesList, message);
        return build(transactionComponents);
    }

    protected abstract T build(TransactionComponents transactionComponents);

    protected abstract List<PublicKey> getSigners(M message);

    @Getter
    @RequiredArgsConstructor
    protected class TransactionComponents {
        private final List<PublicKey> signers;
        private final List<String> signatures;
        private final M message;
    }

    @Deprecated
    public static LegacyTransactionBuilder getBuilder(Message message) {
        return new LegacyTransactionBuilder(message, MessageSigner.getSigner());
    }

    @Deprecated
    public static TransactionV0Builder getBuilderV0(MessageV0 message) {
        return new TransactionV0Builder(message);
    }

    @Deprecated
    public static Transaction build(Message message, List<Account> signers) {
        LegacyTransactionBuilder builder = getBuilder(message);
        signers.forEach(builder::sign);
        return builder.build();
    }

    @Deprecated
    public static Transaction build(Message message, Account signer) {
        return getBuilder(message).sign(signer).build();
    }

    @Deprecated
    public static TransactionV0 buildV0(MessageV0 message, List<Account> signers) {
        TransactionV0Builder builder = getBuilderV0(message);
        signers.forEach(builder::sign);
        return builder.build();
    }

    @Deprecated
    public static TransactionV0 build(MessageV0 message, Account signer) {
        return getBuilderV0(message).sign(signer).build();
    }

}
