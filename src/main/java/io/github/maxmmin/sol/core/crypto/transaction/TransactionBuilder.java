package io.github.maxmmin.sol.core.crypto.transaction;

import io.github.maxmmin.sol.core.crypto.Account;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.sign.MessageSignException;
import io.github.maxmmin.sol.core.crypto.sign.MessageSigner;

import java.util.*;

public class TransactionBuilder {
    private final Map<PublicKey, String> signatures;
    private final Message message;
    private final MessageSigner messageSigner;
    private final byte[] messageBytes;

    public TransactionBuilder(Message message) {
        this(message, new MessageSigner());
    }

    public TransactionBuilder(Message message, MessageSigner messageSigner) {
        this.message = message;
        this.messageSigner = messageSigner;
        this.messageBytes = MessageSerializer.serialize(message);
        this.signatures = new HashMap<>();
        getSigners(message).forEach(signer -> signatures.put(signer, null));
    }

    public TransactionBuilder sign(Account account) {
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

    public Transaction build () throws IllegalStateException {
        if (getSignersCount() != signatures.size()) throw new IllegalStateException("Signers and signatures sizes diff");
        List<String> signaturesList = new ArrayList<>(signatures.size());
        List<PublicKey> signers = getSigners(message);
        for (int i = 0; i < signers.size(); i++) {
            PublicKey signer = signers.get(i);
            String signature = signatures.get(signer);
            if (signature == null) throw new IllegalStateException(
                    String.format("No signature found for signer %s (index %d)", signer.toString(), i)
            );
        }
        return new Transaction(signaturesList, message);
    }

    protected int getSignersCount() {
        return getSignersCount(message);
    }

    private static int getSignersCount(Message message) {
        return Byte.toUnsignedInt(message.getMessageHeader().getNumRequiredSignatures());
    }

    private static List<PublicKey> getSigners(Message message) {
        return message.getAccountKeys().subList(0, getSignersCount(message));
    }
}
