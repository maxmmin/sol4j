package io.github.maxmmin.sol.core.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public class Account {
    private final byte[] publicKey;
    private final byte[] privateKey;

    public Account(byte[] secretKey) {
        if (secretKey.length != 64) throw new IllegalArgumentException("Secret key must be 64 bytes");
        ByteBuffer buffer = ByteBuffer.allocate(secretKey.length);

        byte[] pubKey = new byte[32];
        buffer.get(pubKey, 0, 32);
        this.publicKey = pubKey;

        byte[] privateKey = new byte[32];
        buffer.get(privateKey, 0, 32);
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return new PublicKey(publicKey);
    }

    public byte[] getPrivateKey() {
        return Arrays.copyOfRange(privateKey, 0, 32);
    }
}
