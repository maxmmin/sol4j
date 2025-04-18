package io.github.maxmmin.sol.core.crypto.sign;

import io.github.maxmmin.sol.core.crypto.transaction.Message;
import io.github.maxmmin.sol.core.crypto.transaction.MessageSerializer;
import io.github.maxmmin.sol.util.Base58;

import java.security.InvalidKeyException;

public class MessageSigner {
    private final Signer signer;

    public MessageSigner() {
        this.signer = new JavaEd25519Signer();
    }

    public MessageSigner(Signer signer) {
        this.signer = signer;
    }

    public String sign(Message message, byte[] secretKey) throws MessageSignException {
        byte[] messageBytes = MessageSerializer.serialize(message);
        return sign(messageBytes, secretKey);
    }

    public String sign(byte[] messageBytes, byte[] secretKey) throws MessageSignException {
        byte[] signedBytes = null;
        try {
            signedBytes = signer.sign(messageBytes, secretKey);
        } catch (InvalidKeyException e) {
            throw new MessageSignException("Message signing was failed", e);
        }
        return Base58.encodeToString(signedBytes);
    }
}
