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

    public String sign(Message message, byte[] secretKey) throws InvalidKeyException {
        byte[] messageBytes = MessageSerializer.serialize(message);
        byte[] signedBytes = signer.sign(messageBytes, secretKey);
        return Base58.encodeToString(signedBytes);
    }
}
