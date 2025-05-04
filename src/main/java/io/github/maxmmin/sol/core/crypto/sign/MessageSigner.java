package io.github.maxmmin.sol.core.crypto.sign;

import io.github.maxmmin.sol.core.crypto.transaction.message.Message;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageSerializer;
import io.github.maxmmin.sol.core.crypto.Base58;
import io.github.maxmmin.sol.core.crypto.transaction.message.MessageV0;

import java.security.InvalidKeyException;

public class MessageSigner<V> {
    private final Signer signer;
    private final MessageSerializer<V> serializer;

    public MessageSigner(MessageSerializer<V> serializer) {
        this(new JavaEd25519Signer(), serializer);
    }

    public MessageSigner(Signer signer, MessageSerializer<V> serializer) {
        this.signer = signer;
        this.serializer = serializer;
    }

    public String sign(V message, byte[] secretKey) throws MessageSignException {
        byte[] messageBytes = serializer.serialize(message);
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

    public static MessageSigner<MessageV0> getSignerV0() {
        return new MessageSigner<>(MessageSerializer.getSerializerV0());
    }

    public static MessageSigner<Message> getSigner() {
        return new MessageSigner<>(MessageSerializer.getSerializer());
    }
}
